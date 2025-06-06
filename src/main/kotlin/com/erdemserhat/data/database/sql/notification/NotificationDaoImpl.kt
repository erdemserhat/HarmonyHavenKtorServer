package com.erdemserhat.data.database.sql.notification

import com.erdemserhat.models.Notification
import com.erdemserhat.data.database.sql.MySqlDatabaseConfig
import org.ktorm.dsl.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.logging.Logger

class NotificationDaoImpl : NotificationDao {
    override suspend fun addNotification(notification: Notification): Int {
        return MySqlDatabaseConfig.ktormDatabase.insert(DBNotificationTable) {
            set(DBNotificationTable.user_id, notification.userId)
            set(DBNotificationTable.title, notification.title)
            set(DBNotificationTable.content, notification.content)
            set(DBNotificationTable.is_read, notification.isRead)
            set(DBNotificationTable.timeStamp, notification.timeStamp)
            set(DBNotificationTable.screen_code, notification.screenCode)
        }
    }

    override suspend fun getNotificationSize(userId: Int): Int {
        return MySqlDatabaseConfig.ktormDatabase
            .from(DBNotificationTable)
            .select()
            .where { DBNotificationTable.user_id eq userId }
            .totalRecords
    }

    override suspend fun deleteNotification(notificationId: Int): Boolean {
        val affectedRows = MySqlDatabaseConfig.ktormDatabase.delete(DBNotificationTable) {
            DBNotificationTable.id eq notificationId
        }
        return affectedRows > 0
    }


    override suspend fun getDaysSinceOldestNotification(userId: Int): Long? {
        val oldestTimestamp = MySqlDatabaseConfig.ktormDatabase
            .from(DBNotificationTable)
            .select(DBNotificationTable.timeStamp)
            .where { DBNotificationTable.user_id eq userId }
            .orderBy(DBNotificationTable.timeStamp.asc())
            .limit(0, 1)
            .map { row -> row[DBNotificationTable.timeStamp] }
            .firstOrNull()

        if (oldestTimestamp == null) {
            return null
        }


        // Milisaniye-saniye ayrımı kontrolü
        val adjustedTimestamp = if (oldestTimestamp < 1000000000000L) {
            oldestTimestamp * 1000
        } else {
            oldestTimestamp
        }

        val notificationDate = Instant.ofEpochMilli(adjustedTimestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val today = LocalDate.now()
        val days = ChronoUnit.DAYS.between(notificationDate, today)


        return days
    }


    override suspend fun updateNotification(notification: Notification): Boolean {
        try {
            MySqlDatabaseConfig.ktormDatabase.update(DBNotificationTable) {
                set(DBNotificationTable.title, notification.title)
                set(DBNotificationTable.content, notification.content)
                where {
                    DBNotificationTable.id eq notification.id
                }


            }
            return true

        } catch (e: Exception) {
            return false
        }

    }



    override suspend fun getNotifications(userId: Int, page: Int, size: Int): List<DBNotificationEntity> {
        // Sayfa numarasına ve boyutuna göre offset hesapla
        val offset = (page - 1) * size
        // Veritabanı sorgusunu yap
        val notifications = MySqlDatabaseConfig.ktormDatabase
            .from(DBNotificationTable)
            .select()
            .where { DBNotificationTable.user_id eq userId }
            .orderBy(DBNotificationTable.timeStamp.desc()) // Sıralama ekleyin
            .limit(offset, size)  // Limit ve offset ile sayfalama yap
            .map { row ->
                DBNotificationTable.createEntity(row)  // `DBNotificationEntity` döndürür
            }




        return notifications
    }

    override suspend fun markAsRead(notificationId: Int):Boolean {
        return MySqlDatabaseConfig.ktormDatabase.update(DBNotificationTable){
            set(DBNotificationTable.is_read, true)
            where {
                DBNotificationTable.id eq notificationId
            }
        }>0
    }
}