package com.erdemserhat.data.cache

import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay


suspend fun sendWelcomeNotification(email: String) {
    delay(10000)
    val messageList: List<FcmNotification> = arrayListOf(
        FcmNotification(
            title = "Harmony Haven'a Hoş Geldin *name!",
            body = "Seni burada görmek çok güzel *name, artık sen de bir Harmonier'sin. Burada olduğuna pişman olmayacaksın! Burası inşa edenler kulübü; birlikte, her gün dünden daha iyisini başaracağız.",
            screen = "1"

        ),
        FcmNotification(
            title = "Hoş Geldin *name!",
            body = "*name, Harmony Haven'a katıldığın için çok mutluyuz! Burada seni bekleyen özenle seçilmiş alıntılar ve ilham verici içerikler ile dolu bir dünya var. Her gün seni motive edecek ve hayatına pozitif dokunuşlar yapacak bilgiler bulacaksın. Aramıza hoş geldin!",
            screen = "1"

        ),

        FcmNotification(
            title = "Hoş Geldin *name!",
            body = "*name, Harmony Haven'a katıldığın için çok mutluyuz! Burada seni bekleyen özenle seçilmiş alıntılar ve ilham verici içerikler ile dolu bir dünya var. Her gün seni motive edecek ve hayatına pozitif dokunuşlar yapacak bilgiler bulacaksın. Aramıza hoş geldin!",
            screen = "1"

        )
    )

    val randomIndex = (Math.random() * messageList.size).toInt()
    val selectedMessage = messageList[randomIndex]

    try {
        val specificNotificationData =
            SendNotificationSpecific(email, selectedMessage)
        FirebaseMessaging.getInstance().send(specificNotificationData.toFcmMessage())

    } catch (e: Exception) {
        e.printStackTrace()
    }


}