package com.erdemserhat.routes.admin

import com.erdemserhat.dto.requests.*
import com.erdemserhat.service.di.DatabaseModule
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun Route.sendNotificationGeneralV1() {
    authenticate {
        route("/notification/send-general") {
            post {

                val principal = call.principal<JWTPrincipal>()
                val role = principal?.payload?.getClaim("role")?.asString()

                // Check if the authenticated user has admin role
                if (role != "admin") {
                    call.respond(
                        status = HttpStatusCode.Unauthorized,
                        message = "You are not allowed to use this service"
                    )
                    return@post
                }


                val fcmNotification = call.receiveNullable<FcmNotification>() ?: run {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = "Invalid notification data"
                    )
                    return@post
                }


                try {

                    GlobalScope.launch(Dispatchers.IO) {
                        val users = DatabaseModule.userRepository.getAllUsers().filter { it.fcmId.length > 10 }

                        val deferred = users.map {
                            async {
                                val specificNotification = SendNotificationSpecific(
                                    it.email, fcmNotification)
                                FirebaseMessaging.getInstance().send(specificNotification.toFcmMessage())
                            }
                        }

                        // Tüm görevlerin tamamlanmasını bekle
                        deferred.awaitAll()


                    }


                    call.respond(HttpStatusCode.OK)


                } catch (e: Exception) {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = "Failed to send notifications: ${e.localizedMessage}"
                    )
                }
            }
        }
    }
}
