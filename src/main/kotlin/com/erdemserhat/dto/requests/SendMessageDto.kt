package com.erdemserhat.dto.requests

import com.erdemserhat.service.di.DatabaseModule
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.not


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
    val screen: String=""
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

fun SendNotificationSpecific.toFcmMessage(): Message {
    val user = DatabaseModule.userRepository.getUserByEmailInformation(email)!!
    val userId = user.id
    val specializedTitle =
        notification.title
            .replace("*name", user.name)
            .replace("*surname", user.surname)

    val specializedBody =
        notification.body
            .replace("*name", user.name)
            .replace("*surname", user.surname)


    // Save to database
    DatabaseModule.notificationRepository.addNotification(
        com.erdemserhat.models.Notification(
            id = 0,
            userId = userId,
            title = specializedTitle,
            content = specializedBody,
            isRead = false,
            timeStamp = System.currentTimeMillis() / 1000
        )
    )

    // Create the data payload
    val data = mapOf(
        "title" to specializedTitle,
        "body" to specializedBody,
        "image" to notification.image,
        "screen" to notification.screen
    )

    return Message.builder()
        .putAllData(data) // Set data payload
        .setToken(user.fcmId) // FCM token of the recipient
        .build()
}

