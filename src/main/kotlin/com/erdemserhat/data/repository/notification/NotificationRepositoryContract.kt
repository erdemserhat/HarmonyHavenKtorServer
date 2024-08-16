package com.erdemserhat.data.repository.notification

import com.erdemserhat.models.Notification

interface NotificationRepositoryContract {
    fun addNotification(notification: Notification):Boolean
    fun deleteNotification(notificationId:Int):Boolean
    fun updateNotification(notification: Notification):Boolean
    fun getNotifications(userId: Int,page:Int,size:Int):List<Notification>
    fun markAsRead(notificationId:Int):Boolean
}