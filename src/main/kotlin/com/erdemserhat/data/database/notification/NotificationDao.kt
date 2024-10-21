package com.erdemserhat.data.database.notification

import com.erdemserhat.models.Notification

interface NotificationDao {

    suspend fun addNotification(notification: Notification): Int
    suspend fun deleteNotification(notificationId: Int): Boolean
    suspend fun updateNotification(notification: Notification): Boolean
    suspend fun getNotifications(userId: Int, page: Int, size: Int): List<DBNotificationEntity>
    suspend fun markAsRead(notificationId: Int): Boolean

}