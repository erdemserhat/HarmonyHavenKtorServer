package com.erdemserhat.data.database.nosql.moods.moods

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesCollection
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesRepositoryImpl
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesRepositoryImpl.Companion
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesRepositoryImpl.Companion.NOTIFICATION_PREFERENCES
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue

class MoodsRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase

) : MoodsRepository {

    companion object {
        const val MOODS = "moods"
    }

    override suspend fun addMood(mood: MoodsCollection): BsonValue? {
        try {
            val result = mongoDatabase.getCollection<MoodsCollection>(
                MoodsRepositoryImpl.MOODS
            )
                .insertOne(mood)

            return result.insertedId

        } catch (e: Exception) {
            System.err.println("Failed to insert notification preferences")
            e.printStackTrace()
            return null
        }
    }

    override suspend fun getMoods(): List<MoodsCollection> {
        val preferences = mongoDatabase.getCollection<MoodsCollection>(MOODS)
            .find().toList()
        return preferences

    }
}