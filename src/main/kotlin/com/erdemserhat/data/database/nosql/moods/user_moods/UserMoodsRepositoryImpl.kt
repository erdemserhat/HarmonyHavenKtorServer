package com.erdemserhat.data.database.nosql.moods.user_moods

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.erdemserhat.data.database.nosql.moods.moods.MoodsCollection
import com.erdemserhat.data.database.nosql.moods.moods.MoodsRepositoryImpl
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesCollection
import com.erdemserhat.data.database.nosql.notification_preferences.NotificationPreferencesRepositoryImpl.Companion.NOTIFICATION_PREFERENCES
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue
import org.bson.types.ObjectId

class UserMoodsRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase

) : UserMoodsRepository {


    companion object {
        const val USER_MOODS = "user_moods"
    }


    override suspend fun getUserMood(userId: Int): UserMoodsCollection? {
        val result = mongoDatabase.getCollection<UserMoodsCollection>(
            UserMoodsRepositoryImpl.USER_MOODS
        ).find(Filters.eq("userId", userId)).toList()

        return result.firstOrNull()


    }

    override suspend fun updateUserMood(userId: Int, moodId: ObjectId): BsonValue? {
        val collection = mongoDatabase.getCollection<UserMoodsCollection>(USER_MOODS)

        val filter = Filters.eq("userId", userId)
        val update = Updates.set(UserMoodsCollection::moodId.name, moodId)
        val options = UpdateOptions().upsert(true)

        val result = collection.updateOne(filter, update, options)
        return result.upsertedId
    }

}