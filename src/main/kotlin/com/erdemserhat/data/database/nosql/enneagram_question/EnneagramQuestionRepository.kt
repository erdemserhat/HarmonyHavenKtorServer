package com.erdemserhat.data.database.nosql.enneagram_question

import org.bson.BsonValue
import org.bson.types.ObjectId

interface EnneagramQuestionRepository {
    suspend fun findByQuestionCategory(questionCategory: EnneagramQuestionCategory):List<EnneagramQuestionCollection>?
    suspend fun insertOne(enneagramQuestion: EnneagramQuestionCollection): BsonValue?
    suspend fun deleteById(objectId: ObjectId): Long
    suspend fun findById(objectId: ObjectId): EnneagramQuestionCollection?
    suspend fun updateOne(objectId: ObjectId, enneagramQuestion: EnneagramQuestionCollection): Long
}