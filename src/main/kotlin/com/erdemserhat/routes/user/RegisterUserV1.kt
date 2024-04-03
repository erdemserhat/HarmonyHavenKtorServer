package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.UserAuthenticationInputValidatorService
import com.erdemserhat.domain.validation.UserInformationValidatorService
import com.erdemserhat.domain.validation.ValidationResult
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.models.toUser
import com.erdemserhat.security.hashPassword
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

/**
 * Route handler for user registration.
 */
fun Route.registerUserV1() {
    post("/api/v1/user/register") {
        try {
            // Receive user registration data from the request body
            val newUser = call.receive<UserInformationSchema>()

            // Validate user registration data
            val validator = UserInformationValidatorService(newUser)
            val validationResult = validator.validateForm()

            // Check if all validation conditions are met
            if (!validationResult.isValid) {
                // Respond with unprocessable entity status code and validation result
                call.respond(
                    status = HttpStatusCode.UnprocessableEntity,
                    message = RegistrationResponse(
                        formValidationResult = validationResult,
                        isRegistered = false
                    )
                )
                return@post
            }

            // Hash the user's password before storing it in the database
            val hashedPassword = hashPassword(newUser.password)

            // Add user to the repository
            userRepository.addUser(newUser.toUser().copy(password = hashedPassword))

            // Respond with a success message
            call.respond(
                status = HttpStatusCode.Created,
                message = RegistrationResponse(
                    formValidationResult = validationResult,
                    isRegistered = true
                )
            )

            // Uncomment the line below to send a welcome email to the user
            // sendWelcomeMail(newUser.email, newUser.name)

        } catch (e: Exception) {
            // Respond with internal server error status code and error message
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

/**
 * Data class representing the response of user registration.
 */
@Serializable
data class RegistrationResponse(
    val formValidationResult: ValidationResult,
    val isRegistered: Boolean,
)
