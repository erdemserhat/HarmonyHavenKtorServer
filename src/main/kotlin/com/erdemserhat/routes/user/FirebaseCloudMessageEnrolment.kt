package com.erdemserhat.routes.user

import com.erdemserhat.dto.requests.FcmSetupDto
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fcmEnrolment() {
    authenticate {
        post("/user/fcm-enrolment") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")!!.asString()
            val fcmData = call.receive<FcmSetupDto>()

            if (!DatabaseModule.userRepository.enrolFcm(email, fcmData.fcmID))
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    "Please try again, an error occurred"
                )

            call.respond(
                status = HttpStatusCode.OK,
                "Your fcm id is connected to $email"
            )


        }


    }

}