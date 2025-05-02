package com.erdemserhat.data.database.nosql.enneagram_famous_people

import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory
import com.erdemserhat.service.enneagram.EnneagramFamousPeopleDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class EnneagramFamousPeopleCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val name: String,
    val imageUrl: String,
    val enneagramType: EnneagramType,
    val desc: String
)

