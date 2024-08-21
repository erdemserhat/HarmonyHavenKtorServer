package com.erdemserhat.routes.quote

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteQuotes() {
    authenticate {
        delete("/delete-quotes") {
            val principal = call.principal<JWTPrincipal>()
            val role = principal?.payload?.getClaim("role")?.asString()

            // Verify if the authenticated user has the admin role
            if (role != "admin") {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = "You are not authorized to use this service."
                )
                return@delete
            }

            try {
                DatabaseModule.quoteRepository.deleteAll()
                call.respond(
                    status = HttpStatusCode.OK,
                    message = mapOf("successful" to true)
                )
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }
}
