package com.erdemserhat.data.database.sql.notification

import com.erdemserhat.models.Notification

interface NotificationDao {

    suspend fun getDaysSinceOldestNotification(userId: Int): Long?
    suspend fun getNotificationSize(userId: Int): Int
    suspend fun addNotification(notification: Notification): Int
    suspend fun deleteNotification(notificationId: Int): Boolean
    suspend fun updateNotification(notification: Notification): Boolean
    suspend fun getNotifications(userId: Int, page: Int, size: Int): List<DBNotificationEntity>
    suspend fun markAsRead(notificationId: Int): Boolean

}