package com.erdemserhat.romote.repository.notification

import com.erdemserhat.models.Notification
import com.erdemserhat.romote.database.notification.DBNotificationEntity

interface NotificationRepositoryContract {
    fun addNotification(notification: Notification):Boolean
    fun deleteNotification(notificationId:Int):Boolean
    fun updateNotification(notification: Notification):Boolean
    fun getNotifications(userId:Int):List<Notification>
    fun markAsRead(notificationId:Int):Boolean
}