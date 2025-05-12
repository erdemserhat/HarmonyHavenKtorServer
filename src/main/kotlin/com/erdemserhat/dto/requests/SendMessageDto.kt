package com.erdemserhat.dto.requests

import com.erdemserhat.service.di.DatabaseModule
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable


@Serializable
data class SendNotificationGeneralDto(
    val notification: FcmNotification
)



@Serializable
data class SendNotificationDto(
    val emails: List<String>,
    val notification: FcmNotification
)

@Serializable
data class SendNotificationSpecific(
    val email: String,
    val notification: FcmNotification
)

@Serializable
data class FcmNotification(
    val title: String,
    val body: String,
    val image: String="",
    val screen: String="2"
)

fun SendNotificationGeneralDto.toMessage(): Message {
    return Message.builder()
        .setNotification(
            Notification.builder()
                .setTitle(notification.title)
                .setBody(notification.body)
                .setImage(notification.image)
                .build()
        ).apply {
            setTopic("everyone")
        }.build()

}

suspend fun SendNotificationSpecific.toFcmMessage(): Message {
    // IO dispatcher kullanarak, veritabanı erişimi gibi ağır işlemleri IO thread'de yapıyoruz
    return withContext(Dispatchers.IO) {
        // Kullanıcıyı veritabanından asenkron olarak alıyoruz
        val user = DatabaseModule.userRepository.getUserByEmailInformation(email)
            ?: throw IllegalArgumentException("User not found for email: $email")

        val specializedTitle = notification.title
            .replace("*name", user.name.extractFirstName())
            .replace("*surname", user.surname)

        val specializedBody = notification.body
            .replace("*name", user.name.extractFirstName())
            .replace("*surname", user.surname)


        DatabaseModule.notificationRepository.addNotification(
            com.erdemserhat.models.Notification(
                id = 0,
                userId = user.id,
                title = specializedTitle,
                content = specializedBody,
                isRead = false,
                timeStamp = System.currentTimeMillis() / 1000,
                screenCode = notification.screen
            )
        )

        // Veriyi oluşturuyoruz
        val data = mapOf(
            "title" to specializedTitle,
            "body" to specializedBody,
            "image" to notification.image,
            "screen" to notification.screen
        )

        // FCM mesajını döndürüyoruz
        Message.builder()
            .putAllData(data) // Veriyi ekle
            .setToken(user.fcmId) // Kullanıcının FCM token'i
            .build()
    }
}

private fun String.extractFirstName(): String {
    return this.substringBefore(" ").lowercase().replaceFirstChar { it.uppercase() }
}

