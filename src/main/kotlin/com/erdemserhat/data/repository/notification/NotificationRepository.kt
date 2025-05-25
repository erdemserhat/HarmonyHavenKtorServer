package com.erdemserhat.data.repository.notification

import com.erdemserhat.models.Notification
import com.erdemserhat.data.database.sql.notification.DBNotificationEntity
import com.erdemserhat.data.database.sql.notification.NotificationDao
import com.erdemserhat.data.database.sql.notification.NotificationDaoImpl

class NotificationRepository :NotificationRepositoryContract {
    private val notificationDao:NotificationDao = NotificationDaoImpl()
    override suspend fun addNotification(notification: Notification): Boolean {
        return notificationDao.addNotification(notification)>0
    }

    override suspend fun deleteNotification(notificationId: Int): Boolean {
        return notificationDao.deleteNotification(notificationId)
    }

    override suspend fun updateNotification(notification: Notification): Boolean {
        return notificationDao.updateNotification(notification)
    }

    override suspend fun getNotifications(userId: Int,page:Int,size:Int): List<Notification> {
        return notificationDao.getNotifications(userId,page,size).map { it.toNotification() }
    }

    override suspend fun markAsRead(notificationId: Int): Boolean {
        return notificationDao.markAsRead(notificationId)
    }

    override suspend fun getNotificationSize(userId: Int): Int {
        return notificationDao.getNotificationSize(userId)
    }

    override suspend fun getDaysSinceOldestNotification(userId: Int): Long? {
        return notificationDao.getDaysSinceOldestNotification(userId)
    }

}

fun DBNotificationEntity.toNotification():Notification{
    return Notification(
        id, userId, title, content, isRead, timeStamp,screenCode
    )
}