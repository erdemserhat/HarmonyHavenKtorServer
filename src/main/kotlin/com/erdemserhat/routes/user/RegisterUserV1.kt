package com.erdemserhat.routes.user


import com.erdemserhat.data.mail.sendWelcomeMail
import com.erdemserhat.data.sendWelcomeNotification
import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationDto
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.validation.ValidationResult
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.models.toUser
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.security.hashPassword
import com.erdemserhat.service.validation.UserInformationValidatorService
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Route handler for user registration.
 */
@OptIn(DelicateCoroutinesApi::class)
fun Route.registerUserV1() {
    post("/user/register") {

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


            val uuid: String = UUID.randomUUID().toString()
            val hashedPassword = hashPassword(newUser.password)

            // Hash the user's password before storing it in the database

            // Add user to the repository
            userRepository.addUser(newUser.toUser().copy(password = hashedPassword, uuid = uuid))
            GlobalScope.launch(Dispatchers.IO) {
                val ipAddress = call.request.origin.remoteHost
                sendWelcomeMail(to = newUser.email, name = newUser.name)
                sendWelcomeNotification(email = newUser.email)
                val informationMessage =
                    SendNotificationSpecific(
                        email = "serhaterdem961@gmail.com",
                        notification = FcmNotification(
                            title = "Yeni Bir Üye Katıldı: ${newUser.name} 😊",
                            body = "Merhaba! ${newUser.name}, ${newUser.email} (${ipAddress}) adresiyle Harmony Haven'a katıldı. Bir göz atmak isteyebilirsiniz! 👀",
                            screen = "1"
                        )
                    )

                FirebaseMessaging.getInstance().send(informationMessage.toFcmMessage())


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



