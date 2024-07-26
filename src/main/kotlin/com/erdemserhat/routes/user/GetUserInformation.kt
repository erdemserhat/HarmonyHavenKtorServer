package com.erdemserhat.routes.user

import com.erdemserhat.models.toDto
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserInformation() {
    authenticate {
        get("/user/get-information") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val user = DatabaseModule.userRepository.getUserByEmailInformation(email!!)

            call.respond(
                status = HttpStatusCode.OK,
                message = user!!.toDto()
            )


        }
    }

}