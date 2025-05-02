package com.erdemserhat.data.database.nosql.enneagram_test_result

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue

class EnneagramTestResultRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase,
) : EnneagramTestResultRepository {

    companion object {
        const val ENNEAGRAM_TEST_RESULT_COLLECTION = "enneagram_test_result"
    }

    override suspend fun addTestResult(testResult: EnneagramTestResultCollection): BsonValue? {
        return try {
            val result = mongoDatabase
                .getCollection<EnneagramTestResultCollection>(ENNEAGRAM_TEST_RESULT_COLLECTION)
                .insertOne(testResult)

            return result.insertedId

        } catch (e: Exception) {
            System.err.println(e.localizedMessage)
            null
        }
    }

    override suspend fun getTestResultByUserId(
        userId: Int,
        testCategory: EnneagramTestResultCategory
    ): EnneagramTestResultCollection? {

        val result = mongoDatabase
            .getCollection<EnneagramTestResultCollection>(ENNEAGRAM_TEST_RESULT_COLLECTION)
            .find(
                Filters.and(
                    Filters.eq("userId", userId),
                    Filters.eq("testCategory", testCategory),
                )
            )
            .sort(Sorts.descending("createdAt")) // En yeni tarih en üstte olacak şekilde sırala
            .firstOrNull()

        return result
    }

    override suspend fun getTestResultsByUserId(
        userId: Int,
        testCategory: EnneagramTestResultCategory
    ):List<EnneagramTestResultCollection> {
        val results = mongoDatabase
            .getCollection<EnneagramTestResultCollection>(ENNEAGRAM_TEST_RESULT_COLLECTION)
            .find(
                Filters.and(
                    Filters.eq("userId", userId),
                    Filters.eq("testCategory", testCategory),
                )
            )

        return results.toList()
    }
}