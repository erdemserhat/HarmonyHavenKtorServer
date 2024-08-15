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
fun Route.sendNotificationSpecificV1() {
    authenticate {
        route("/notification/send-specific") {
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

                val specificNotification = call.receive<SendNotificationSpecific>()
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val user = DatabaseModule.userRepository.getAllUsers().filter {
                            it.email == specificNotification.email
                        }

                        val specificNotificationData =
                            SendNotificationSpecific(specificNotification.email, specificNotification.notification)
                        FirebaseMessaging.getInstance().send(specificNotificationData.toFcmMessage())

                    }catch (e:Exception){
                        e.printStackTrace()
                    }



                }

                call.respond(HttpStatusCode.OK)


            }
        }
    }
}
