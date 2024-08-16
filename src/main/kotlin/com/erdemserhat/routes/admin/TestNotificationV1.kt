package com.erdemserhat.routes.admin

import com.erdemserhat.dto.requests.*
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.openai.OpenAIPrompts
import com.erdemserhat.service.openai.OpenAIRequest
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun Route.testNotificationV1() {

    route("/notification/send-ai") {
        post {
            //val request = call.receive<UserAuthenticationRequest>()


            if(true){
                GlobalScope.launch(Dispatchers.IO) {





                    val promptList = listOf(
                        OpenAIPrompts.ABOUT_LIFE,
                        OpenAIPrompts.POSITIVE_AFFIRMATION,
                        OpenAIPrompts.QUOTE_MESSAGE,
                        OpenAIPrompts.INFORMATION_MESSAGE


                    )


                    val randomPromptIx = (Math.random() * promptList.size).toInt()
                    val promptAnswer = OpenAIRequest(promptList[randomPromptIx]).getResult()

                    val notifications = SendNotificationDto(
                        emails = DatabaseModule.userRepository.getAllUsers().filter { it.fcmId.length > 10 }
                            .map { it.email },
                        notification = FcmNotification(
                            title = "*name",
                            body = promptAnswer
                        )
                    )

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

                call.respond("Sent :)")

            }else{
                call.respond("Not Sent :(")

            }




        }


    }
}
