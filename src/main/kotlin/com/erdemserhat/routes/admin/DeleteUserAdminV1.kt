package com.erdemserhat.routes.admin

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.isValidEmailFormat
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Defines the route for deleting a user by an admin.
 */
fun Route.deleteUserAdmin() {
    authenticate {
        delete("/api/v1/admin/delete-user") {
            try {
                // Get the role of the authenticated user
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.payload?.getClaim("role")?.asString()

                // Receive data from the request body
                val data = call.receive<Map<String, String>>()
                val emailToDelete = data["email"]

                // Check if the authenticated user has admin role
                if (role != "admin") {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = "You are not allowed to use this service"
                    )
                    return@delete
                }

                // Check if email information is provided
                if (emailToDelete == null) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = "Email information must be provided to use this service"
                    )
                    return@delete
                }

                // Validate email format
                if (!isValidEmailFormat(emailToDelete)) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = "Invalid email format"
                    )
                    return@delete
                }

                // Check if the user exists in the system
                if (!userRepository.controlUserExistenceByEmail(emailToDelete)) {
                    call.respond(
                        status = HttpStatusCode.UnprocessableEntity,
                        message = "User is not found in the system"
                    )
                    return@delete
                }

                // Delete the user from the system
                if (userRepository.deleteUserByEmailInformation(emailToDelete)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = "User ($emailToDelete) successfully deleted."
                    )
                }

            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = "Internal Server Error; please check your request body and try again"
                )
            }
        }
    }
}
