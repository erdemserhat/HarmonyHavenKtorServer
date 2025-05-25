package com.erdemserhat.data.database.nosql.moods.user_moods

import com.erdemserhat.data.database.nosql.moods.moods.MoodsCollection
import org.bson.BsonValue
import org.bson.types.ObjectId

interface UserMoodsRepository {
    suspend fun getUserMood(userId:Int): UserMoodsCollection?
    suspend fun updateUserMood(userId:Int, moodId:ObjectId):BsonValue?
}