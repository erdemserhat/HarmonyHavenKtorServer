package com.erdemserhat.models

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kotlinx.serialization.Serializable


@Serializable
data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

@Serializable
data class NotificationBody(
    val title: String,
    val body: String,
    val image: String
)

fun SendMessageDto.toMessage(): Message {
    return Message.builder()
        .setNotification(
            Notification.builder()
                .setTitle(notification.title)
                .setBody(notification.body)
                .setImage(notification.image)
                .build()
        ).apply {
            if (to == null) {
                setTopic("everyone")
            } else {
                setToken(to)

            }
        }.build()


}


