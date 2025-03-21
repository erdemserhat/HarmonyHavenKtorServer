package com.erdemserhat.service

import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.openai.OpenAIRequest
import com.erdemserhat.service.openai.OpenAIValidationRequest
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import java.lang.System.err
import kotlin.coroutines.coroutineContext

suspend fun sendAIBasedMessage(
    notificationAI: NotificationAI,
    sendSpecificByEmailList: List<String> = emptyList(),
    justPrint: Boolean = false,

) {
    //you must be inside a corutine scope to start a coroutine (async and launch)
    coroutineScope {
        var body = ""

        do {
            body = OpenAIRequest(notificationAI.prompt).getResult().replace(Regex("Ali"), "*name")
        } while (!validateAIResponse(body))


        val title = notificationAI.title


        val fcmNotification = FcmNotification(
            title = "Harmony Haven",
            body = body,
            image = "",
            screen = "1"
        )
        if(justPrint) {
            println(fcmNotification.title)
            println(fcmNotification.body)

        }else{
            if (sendSpecificByEmailList.isEmpty()) {
                val deferredUser = async(Dispatchers.IO){
                    DatabaseModule.userRepository.getAllUsers().filter { it.fcmId.length > 10 }
                }
                val semaphore = Semaphore(10) //maximum 10 coroutine
                deferredUser.await().forEach {
                    //creates new coroutines for every request as asynchronously which is child of coroutineScope
                    launch() {
                        semaphore.acquire() // take semaphore
                        try {
                            val specificNotification = SendNotificationSpecific(it.email, fcmNotification)
                            FirebaseMessaging.getInstance().send(specificNotification.toFcmMessage())
                        } catch (e: Exception) {
                            err.println("sendAIBasedMessage(): ${e.message}")
                        } finally {
                            semaphore.release() //release semaphore
                        }
                    }
                }


            } else {

                val semaphore = Semaphore(10)
                sendSpecificByEmailList.forEach { email ->
                    launch {
                        semaphore.acquire()
                        try {
                            val specificNotification = SendNotificationSpecific(
                                email, fcmNotification
                            )
                            FirebaseMessaging.getInstance().send(specificNotification.toFcmMessage())
                        } catch (e: Exception) {
                            err.println("sendAIBasedMessage(): ${e.message}")
                        } finally {
                            semaphore.release() //release semaphore
                        }

                    }
                }


            }

        }



    }
}

private suspend fun validateAIResponse(message: String): Boolean {
    val disallowedPhrases = listOf(
        "Elbette!", "Tabii ki!", "Kesinlikle!", "Tabii!", "Peki!",
        "Elbette ki!", "Anlaşıldı!", "Tabii tabii!", "Memnuniyetle!",
        "Doğal olarak!", "Aynen öyle!", "Şüphesiz!", "Tabi ki!", "Tabii efendim!"
    )

    // Eğer mesaj, yasaklı giriş cümlelerinden birini içeriyorsa false döner

    val openAIValidationRequest = OpenAIValidationRequest(message)
    val isLogicalResult = openAIValidationRequest.getResult()


        val isLogical = isLogicalResult.contains("true")
    println("----><<<>"+isLogical)





    return disallowedPhrases.none { message.contains(it, ignoreCase = true) } && isLogical
}


data class NotificationAI(
    val prompt: String,
    val title: String
)


