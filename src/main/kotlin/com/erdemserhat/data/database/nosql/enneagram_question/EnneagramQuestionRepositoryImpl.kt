package com.erdemserhat.data.database.nosql.enneagram_question

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue
import org.bson.types.ObjectId

class EnneagramQuestionRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase,
) : EnneagramQuestionRepository {

    companion object {
        const val ENNEAGRAM_QUESTION_COLLECTION = "enneagram_questions"
    }

    override suspend fun findByQuestionCategory(questionCategory: EnneagramQuestionCategory): List<EnneagramQuestionCollection>? {
        return try {
            mongoDatabase
                .getCollection<EnneagramQuestionCollection>(ENNEAGRAM_QUESTION_COLLECTION)
                .find(Filters.eq("enneagramQuestionCategory", questionCategory)).toList()

        } catch (ex: MongoException) {
            System.err.println("Unable to find due to an error: $ex")
            null


        }
    }


    override suspend fun insertOne(enneagramQuestion: EnneagramQuestionCollection): BsonValue? {
        try {
            val result =
                mongoDatabase.getCollection<EnneagramQuestionCollection>(ENNEAGRAM_QUESTION_COLLECTION).insertOne(
                    enneagramQuestion
                )
            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }
        return null
    }

    override suspend fun deleteById(objectId: ObjectId): Long {
        try {
            val result =
                mongoDatabase.getCollection<EnneagramQuestionCollection>(ENNEAGRAM_QUESTION_COLLECTION).deleteOne(
                    Filters.eq("_id", objectId)
                )
            return result.deletedCount
        } catch (e: MongoException) {
            System.err.println("Unable to delete due to an error: $e")
        }
        return 0
    }

    override suspend fun findById(objectId: ObjectId): EnneagramQuestionCollection? {
        return mongoDatabase.getCollection<EnneagramQuestionCollection>(ENNEAGRAM_QUESTION_COLLECTION)
            .withDocumentClass<EnneagramQuestionCollection>()
            .find(Filters.eq("_id", objectId))
            .firstOrNull()
    }

    override suspend fun updateOne(objectId: ObjectId, enneagramQuestion: EnneagramQuestionCollection): Long {
        try {
            val query = Filters.eq("_id", objectId)
            val updates = Updates.combine(
                Updates.set(
                    EnneagramQuestionCollection::enneagramQuestionCategory.name,
                    enneagramQuestion.enneagramQuestionCategory
                ),
                Updates.set(EnneagramQuestionCollection::personalityNumber.name, enneagramQuestion.personalityNumber),
                Updates.set(EnneagramQuestionCollection::content.name, enneagramQuestion.content)
            )
            val options = UpdateOptions().upsert(true)
            val result =
                mongoDatabase.getCollection<EnneagramQuestionCollection>(ENNEAGRAM_QUESTION_COLLECTION)
                    .updateOne(query, updates, options)
            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to update due to an error: $e")
        }
        return 0
    }


}