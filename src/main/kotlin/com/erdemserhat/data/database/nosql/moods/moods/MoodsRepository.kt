package com.erdemserhat.data.database.nosql.moods.moods

import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesCollection
import org.bson.BsonValue

interface MoodsRepository {
    suspend fun addMood(mood: MoodsCollection): BsonValue?
    suspend fun getMoods():List<MoodsCollection>
}