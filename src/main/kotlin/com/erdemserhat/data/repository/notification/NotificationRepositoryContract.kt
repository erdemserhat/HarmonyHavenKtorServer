package com.erdemserhat.data.repository.notification

import com.erdemserhat.models.Notification

interface NotificationRepositoryContract {
    suspend fun addNotification(notification: Notification):Boolean
    suspend fun deleteNotification(notificationId:Int):Boolean
    suspend fun updateNotification(notification: Notification):Boolean
    suspend fun getNotifications(userId: Int,page:Int,size:Int):List<Notification>
    suspend fun markAsRead(notificationId:Int):Boolean
}