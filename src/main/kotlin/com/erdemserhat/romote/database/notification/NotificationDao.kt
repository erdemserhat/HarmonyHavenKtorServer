package com.erdemserhat.romote.database.notification

import com.erdemserhat.models.Notification

interface NotificationDao {

    fun addNotification(notification: Notification):Int
    fun deleteNotification(notificationId:Int):Boolean
    fun updateNotification(notification: Notification):Boolean
    fun getNotifications(userId: Int,page:Int,size:Int):List<DBNotificationEntity>
    fun markAsRead(notificationId:Int):Boolean

}