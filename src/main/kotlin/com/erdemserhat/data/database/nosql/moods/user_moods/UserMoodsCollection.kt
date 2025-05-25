package com.erdemserhat.data.database.nosql.moods.user_moods

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class UserMoodsCollection(
    @BsonId @Contextual val id: ObjectId = ObjectId(),
    val userId: Int,
    @Contextual
    val moodId: ObjectId
)