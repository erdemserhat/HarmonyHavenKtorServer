package com.erdemserhat.models

import com.erdemserhat.dto.responses.NotificationDto
import kotlinx.serialization.Serializable
import org.apache.commons.net.ntp.TimeStamp
import java.time.Instant


@Serializable
data class Notification(
    val id:Int,
    val userId:Int,
    val title:String,
    val content:String,
    val isRead:Boolean,
    val timeStamp:Long
)


fun Notification.toDto(): NotificationDto {
    return NotificationDto(id,title,content,timeStamp)
}