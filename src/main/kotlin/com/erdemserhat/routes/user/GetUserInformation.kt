package com.erdemserhat.routes.user

import com.erdemserhat.models.toDto
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.di.DatabaseModule.userMoodsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId

fun Route.getUserInformation() {
    authenticate {
        get("/user/get-information") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val user = DatabaseModule.userRepository.getUserByEmailInformation(email!!)
            val activeDays = DatabaseModule.notificationRepository.getDaysSinceOldestNotification(user!!.id)

            call.respond(
                status = HttpStatusCode.OK,
                message = user.toDto(
                    activeDays?.toInt() ?: 0
                )
            )

        }

        get("/user/get-mood") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()

            if (email.isNullOrBlank()) {
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token.")
                return@get
            }

            val user = DatabaseModule.userRepository.getUserByEmailInformation(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found.")
                return@get
            }

            val currentMood = userMoodsRepository.getUserMood(user.id)
            if (currentMood == null) {
                call.respond(HttpStatusCode.NotFound, "User mood not found.")
                return@get
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = mapOf("moodId" to currentMood.moodId.toString())
            )
        }

        patch("/user/update-mood") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()

            if (email.isNullOrBlank()) {
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token.")
                return@patch
            }

            val user = DatabaseModule.userRepository.getUserByEmailInformation(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found.")
                return@patch
            }

            val requestBody = call.receive<Map<String, String>>()
            val moodIdString = requestBody["moodId"]

            if (moodIdString.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing 'moodId' in request body.")
                return@patch
            }

            val moodId = try {
                ObjectId(moodIdString)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid moodId format.")
                return@patch
            }

            userMoodsRepository.updateUserMood(userId = user.id, moodId = moodId)

            call.respond(
                status = HttpStatusCode.OK,
                message = mapOf("message" to "Mood updated successfully.", "moodId" to moodId.toString())
            )
        }

        get("/user/get-all-moods") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()

            if (email.isNullOrBlank()) {
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token.")
                return@get
            }

            val user = DatabaseModule.userRepository.getUserByEmailInformation(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found.")
                return@get
            }

            val allMoods = DatabaseModule.moodsRepository.getMoods()

            call.respond(HttpStatusCode.OK, allMoods)
        }



    }

}

