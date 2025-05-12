package com.erdemserhat.data.database.nosql.notification_preferences

import org.bson.BsonValue
import org.bson.types.ObjectId
import java.time.LocalDateTime

interface NotificationPreferencesRepository{
    suspend fun addNotificationPreferences(notificationPreference:NotificationPreferencesCollection): BsonValue?
    suspend fun getAllNotificationPreferences():List<NotificationPreferencesCollection>
    suspend fun getNotificationPreferencesByUserId(userId: Int):List<NotificationPreferencesCollection>
    suspend fun updateLastSentAt(objectId: ObjectId, time: LocalDateTime):BsonValue?
    suspend fun deleteAllNotificationPreferences(objectId: ObjectId,userId: Int): Long
    suspend fun updateNotificationPreferences(notificationPreferences:NotificationPreferencesCollection):Boolean

}