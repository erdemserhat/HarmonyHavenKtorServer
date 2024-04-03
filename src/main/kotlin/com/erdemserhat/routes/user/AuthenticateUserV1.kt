package com.erdemserhat.routes.user

import com.erdemserhat.domain.authentication.UserAuthenticationCredentialsValidatorService
import com.erdemserhat.domain.validation.UserAuthenticationInputValidatorService
import com.erdemserhat.domain.validation.UserAuthenticationJWTService
import com.erdemserhat.domain.validation.ValidationResult
import com.erdemserhat.models.rest.client.UserAuthenticationRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

/**
 * Sets up the route for user authentication using the HTTP POST method.
 */
fun Route.authenticateUserV1() {
    post("/api/v1/user/authenticate") {
        val userAuth = call.receive<UserAuthenticationRequest>()
        try {
            // Initialize services
            val formValidator = UserAuthenticationInputValidatorService(userAuth)
            val credentialValidator = UserAuthenticationCredentialsValidatorService(userAuth)
            val jwtGenerator = UserAuthenticationJWTService(userAuth)

            // Validate user input
            val formValidationResult = formValidator.validateAuthenticationForm()

            if (!formValidationResult.isValid) {
                // Respond with form validation result
                call.respond(
                    status = HttpStatusCode.OK,
                    message = AuthenticationResponse(
                        formValidationResult = formValidationResult,
                        credentialsValidationResult = null,
                        isAuthenticated = false,
                        jwt = null
                    )
                )
                return@post
            }

            // Verify user credentials
            val credentialsValidationResult = credentialValidator.verifyUser()
            if (!credentialsValidationResult.isValid) {
                // Respond with credentials validation result
                call.respond(
                    status = HttpStatusCode.OK,
                    message = AuthenticationResponse(
                        formValidationResult = formValidationResult,
                        credentialsValidationResult = credentialsValidationResult,
                        isAuthenticated = false,
                        jwt = null
                    )
                )
                return@post
            }

            // Generate JWT token
            val jwt = jwtGenerator.generateJWT()

            // Respond with authentication success
            call.respond(
                status = HttpStatusCode.OK,
                message = AuthenticationResponse(
                    formValidationResult = formValidationResult,
                    credentialsValidationResult = credentialsValidationResult,
                    isAuthenticated = true,
                    jwt = jwt
                )
            )
        } catch (e: Exception) {
            // If an exception occurs, respond with the error message
            val message = e.message.toString()
            call.respond(HttpStatusCode.InternalServerError, message)
            e.printStackTrace()
        }
    }
}

/**
 * Data class representing the response of user authentication.
 */
@Serializable
data class AuthenticationResponse(
    val formValidationResult: ValidationResult,
    val credentialsValidationResult: ValidationResult?,
    val isAuthenticated: Boolean,
    val jwt: String?,
)
