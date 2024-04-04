package com.erdemserhat.routes.admin

import com.erdemserhat.dto.requests.*
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
fun Route.sendNotificationV1() {
    authenticate {
        route("/notification/send-specific") {
            post {
                val notifications = call.receiveNullable<SendNotificationDto>() ?: run {

                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }


                // Asenkron olarak işlem yapmak için bir coroutine başlat
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
        }



        route("/notification/send-general") {
            post {
                val body = call.receiveNullable<SendNotificationGeneralDto>() ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                FirebaseMessaging.getInstance().send(body.toMessage())
                call.respond(HttpStatusCode.OK)
            }
        }


    }

