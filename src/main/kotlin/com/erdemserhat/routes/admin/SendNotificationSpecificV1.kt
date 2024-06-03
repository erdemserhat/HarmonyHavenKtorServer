package com.erdemserhat.routes.admin

import com.erdemserhat.dto.requests.*
import com.erdemserhat.models.Notification
import com.erdemserhat.service.di.DatabaseModule
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.not

@OptIn(DelicateCoroutinesApi::class)
fun Route.sendNotificationSpecificV1() {
    //authenticate {
        route("/notification/send-specific") {
            post {
                val notifications = call.receiveNullable<SendNotificationDto>() ?: run {

                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                GlobalScope.launch {
                    val fcmNotification = notifications.notification
                    val deferreds = notifications.emails.map { email ->
                        async {
                            val specificNotification = SendNotificationSpecific(email, fcmNotification)
                            FirebaseMessaging.getInstance().send(specificNotification.toFcmMessage())
                        }
                    }

                    // Tüm görevlerin tamamlanmasını bekle
                    deferreds.awaitAll()
                }



                call.respond(HttpStatusCode.OK)
            }


        }
   // }

}