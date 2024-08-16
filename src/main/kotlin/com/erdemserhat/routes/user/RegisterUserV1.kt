package com.erdemserhat.routes.user

import com.erdemserhat.EncryptionDataDto
import com.erdemserhat.EncryptionToFarawayServerModel
import com.erdemserhat.makeEncryptionRequest
import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.validation.ValidationResult
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.models.toUser
import com.erdemserhat.service.validation.UserInformationValidatorService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Route handler for user registration.
 */
@OptIn(DelicateCoroutinesApi::class)
fun Route.registerUserV1() {
    post("/user/register") {
        println("${YELLOW}---->Registration Request from Client")

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


            val uuid:String = UUID.randomUUID().toString()
            println("${YELLOW}Encryption Request to Caas----->>")
            val hashedPassword = makeEncryptionRequest(
                EncryptionToFarawayServerModel(
                    encryptionData = EncryptionDataDto(
                        sensitiveData = newUser.password,
                        userUUID = uuid
                    )

                )
            )
            println("${YELLOW} <---Encryption Request is Responded by CaaS")

            // Hash the user's password before storing it in the database

            // Add user to the repository
            userRepository.addUser(newUser.toUser().copy(password = hashedPassword, uuid = uuid))
            GlobalScope.launch(Dispatchers.IO) {
                //sendWelcomeMail(to = newUser.email, name = newUser.name)
            }


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
