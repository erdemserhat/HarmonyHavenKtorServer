package com.erdemserhat.routes.user

import com.erdemserhat.service.authentication.UserAuthenticationCredentialsValidatorService
import com.erdemserhat.service.validation.UserAuthenticationInputValidatorService
import com.erdemserhat.service.security.UserAuthenticationJWTService
import com.erdemserhat.dto.requests.UserAuthenticationRequest
import com.erdemserhat.dto.responses.AuthenticationResponse
import com.erdemserhat.service.validation.ValidationResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

/**
 * Sets up the route for user authentication using the HTTP POST method.
 */
val RED = "\u001B[31m"
val GREEN = "\u001B[32m"
val YELLOW = "\u001B[33m"
val RESET = "\u001B[0m"
fun Route.authenticateUserV1() {
    val log = LoggerFactory.getLogger("com.erdemserhat")

    val RED = "\u001B[31m"
    val GREEN = "\u001B[32m"
    val YELLOW = "\u001B[33m"
    val RESET = "\u001B[0m"
    post("/user/authenticate") {
        val userAuth = call.receive<UserAuthenticationRequest>()
        println("${YELLOW}Authentication Request from Client")
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
                println("${RED}Authentication Request Result: Unsuccessfully")
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
                println("${RED}Authentication Request Result: Unsuccessfully")
                return@post
            }

            // Generate JWT token
             val jwt = jwtGenerator.generateJWT()

            // Respond with authentication success
            println("${GREEN}Authentication Request Result: Successfully")
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
