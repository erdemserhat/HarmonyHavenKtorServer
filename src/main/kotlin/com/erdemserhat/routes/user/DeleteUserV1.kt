package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule.userRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Defines the route to handle user deletion.
 */
fun Route.deleteUser() {
    authenticate {
        delete("/api/v1/user/delete") {
            // Extract the user's email from the JWT token payload
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            try {
                // Attempt to delete the user from the database
                if (userRepository.deleteUserByEmailInformation(email!!)) {
                    // If deletion is successful, respond with a success message
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = "User successfully deleted."
                    )
                } else {
                    // If user does not exist, respond with a not found status code
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = "User with this email does not exist."
                    )
                }
            } catch (e: Exception) {
                // If an unexpected error occurs, respond with an internal server error status code
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = e.message.toString()
                )
            }
        }
    }
}
