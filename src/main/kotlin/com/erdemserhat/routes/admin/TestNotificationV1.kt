package com.erdemserhat.routes.admin

import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationDto
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
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
fun Route.TestNotificationV1() {
    route("/test-notification") {
        get {
            call.respond("Sent :)")
            GlobalScope.launch(Dispatchers.IO) {

                val promptList = listOf(
                    OpenAIPrompts.ABOUT_LIFE,
                    OpenAIPrompts.POSITIVE_AFFIRMATION,
                    OpenAIPrompts.INSPIRATIONAL_QUOTE,
                )
                val randomPromptIx = (Math.random()*promptList.size).toInt()
                val promptAnswer = OpenAIRequest(promptList[randomPromptIx]).getResult()

                val notifications = SendNotificationDto(
                    emails = DatabaseModule.userRepository.getAllUsers().filter { it.fcmId.length>10 }.map { it.email },
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




        }


    }


}