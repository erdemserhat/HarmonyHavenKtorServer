package com.erdemserhat.routes.user


import com.erdemserhat.data.mail.sendWelcomeMail
import com.erdemserhat.data.cache.sendWelcomeNotification
import com.erdemserhat.dto.requests.*
import com.erdemserhat.service.di.DatabaseModule.userRepository
import com.erdemserhat.service.validation.ValidationResult
import com.erdemserhat.models.UserInformationSchema
import com.erdemserhat.models.toUser
import com.erdemserhat.service.security.UserAuthenticationJWTService
import com.erdemserhat.service.security.hashPassword
import com.erdemserhat.service.validation.UserInformationValidatorService
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.*
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

            // Kullanıcıyı eklemek için suspend fonksiyon çağrısı
            val isUserAdded = withContext(Dispatchers.IO) {
                try {
                    userRepository.addUser(newUser.toUser().copy(password = hashedPassword, uuid = uuid))
                    true
                } catch (e: Exception) {
                    println("DB Error: ${e.localizedMessage}")
                    false
                }
            }

            if (!isUserAdded) {
                call.respond(HttpStatusCode.InternalServerError, "Kullanıcı eklenirken hata oluştu.")
                return@post
            }

            // Kullanıcı başarıyla eklendiyse JWT oluştur
            val jwtGenerator = UserAuthenticationJWTService(
                userAuth = UserAuthenticationRequest(
                    email = newUser.email,
                    password = newUser.password
                )
            )

            call.response.cookies.append(
                Cookie(
                    maxAge = 36000000,
                    name = "auth_token",
                    value = jwtGenerator.generateJWT(),
                    path = "/",
                    httpOnly = true,
                    secure = true,
                    extensions = mapOf("SameSite" to "None")
                )
            )

            // Arka planda mail ve bildirim gönderme işlemlerini başlat
            call.application.launch(Dispatchers.IO) {
                val ipAddress = call.request.origin.remoteHost
                sendWelcomeMail(to = newUser.email, name = newUser.name)
                sendWelcomeNotification(email = newUser.email)

                val informationMessage = SendNotificationSpecific(
                    email = "serhaterdem961@gmail.com",
                    notification = FcmNotification(
                        title = "Yeni Bir Üye Katıldı: ${newUser.name} 😊",
                        body = "Merhaba! ${newUser.name}, ${newUser.email} (${ipAddress}) adresiyle Harmony Haven'a katıldı. Bir göz atmak isteyebilirsiniz! 👀",
                        screen = "1"
                    )
                )

                FirebaseMessaging.getInstance().send(informationMessage.toFcmMessage())
            }

            call.application.launch {
                sendWelcomeMail(to = newUser.email, name = newUser.name)
            }

            // Kullanıcıya başarılı yanıt gönder
            call.respond(
                status = HttpStatusCode.Created,
                message = RegistrationResponse(
                    formValidationResult = validationResult,
                    isRegistered = true
                )
            )

        } catch (e: Exception) {
            println("Request Error: ${e.localizedMessage}")
            call.respond(HttpStatusCode.InternalServerError, message = e.localizedMessage)
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



