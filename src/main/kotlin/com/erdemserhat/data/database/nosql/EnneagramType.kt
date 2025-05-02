package com.erdemserhat.data.database.nosql

import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId






data class EnneagramTypeDescriptionCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val enneagramType: EnneagramType,
    val description: String
)

data class EnneagramExtraTypeDescription(
    @BsonId val id: ObjectId = ObjectId(),
    val extraType: Int,
    val description: String
)


