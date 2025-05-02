package com.erdemserhat.data.database.nosql.enneagram_question

import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.EnneagramQuestionDto
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class EnneagramQuestionCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val enneagramQuestionCategory: EnneagramQuestionCategory,
    val personalityNumber: Int,
    val content: String,
)
