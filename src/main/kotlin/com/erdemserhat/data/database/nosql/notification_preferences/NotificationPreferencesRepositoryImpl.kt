package com.erdemserhat.data.database.nosql.notification_preferences

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCollection
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionRepositoryImpl.Companion.ENNEAGRAM_QUESTION_COLLECTION
import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue
import org.bson.types.ObjectId
import java.time.LocalDateTime

class NotificationPreferencesRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase,

    ): NotificationPreferencesRepository {

    companion object {
        const val NOTIFICATION_PREFERENCES = "notification_preferences"
    }

    override suspend fun addNotificationPreferences(notificationPreference: NotificationPreferencesCollection): BsonValue? {
        try {
            val result = mongoDatabase.getCollection<NotificationPreferencesCollection>(NOTIFICATION_PREFERENCES)
                .insertOne(notificationPreference)

            return result.insertedId

        }catch (e:Exception){
            System.err.println("Failed to insert notification preferences")
            e.printStackTrace()
            return null
        }

    }

    override suspend fun getAllNotificationPreferences(): List<NotificationPreferencesCollection> {
        val preferences = mongoDatabase.getCollection<NotificationPreferencesCollection>(NOTIFICATION_PREFERENCES)
            .find().toList()

        return preferences

    }

    override suspend fun getNotificationPreferencesByUserId(userId: Int): List<NotificationPreferencesCollection> {
        val preferences = mongoDatabase.getCollection<NotificationPreferencesCollection>(NOTIFICATION_PREFERENCES)
            .find(Filters.eq("userId", userId)).toList()
            .toList()
        return preferences

    }

    override suspend fun updateLastSentAt(objectId: ObjectId, time: LocalDateTime):BsonValue? {
        val collection = mongoDatabase
            .getCollection<NotificationPreferencesCollection>(NOTIFICATION_PREFERENCES)

        val query = Filters.eq("_id", objectId)
        val update = Updates.set(NotificationPreferencesCollection::lastSentAt.name,time)

        val options = UpdateOptions().upsert(true)
        val result =
            collection
                .updateOne(query, update, options)

        return result.upsertedId
    }

    override suspend fun deleteAllNotificationPreferences(objectId: ObjectId,userId: Int): Long {
        try {
            val result =
                mongoDatabase.getCollection<NotificationPreferencesCollection>(NOTIFICATION_PREFERENCES).deleteOne(
                    Filters.and(
                        Filters.eq("_id", objectId),
                        Filters.eq("userId", userId)
                    )
                )
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
        }
        return 0
    }

}