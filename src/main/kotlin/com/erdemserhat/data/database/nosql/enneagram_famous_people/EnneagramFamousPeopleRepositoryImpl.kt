package com.erdemserhat.data.database.nosql.enneagram_famous_people

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.bson.BsonValue

class EnneagramFamousPeopleRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase
):EnneagramFamousPeopleRepository {

    companion object {
        const val ENNEAGRAM_FAMOUS_PEOPLE_COLLECTION = "enneagram_famous_people"
    }

    override suspend fun findByEnneagramType(enneagramType: EnneagramType): List<EnneagramFamousPeopleCollection>? {
        return try {
            mongoDatabase
                .getCollection<EnneagramFamousPeopleCollection>(ENNEAGRAM_FAMOUS_PEOPLE_COLLECTION)
                .find(Filters.eq("enneagramType", enneagramType)).toList()

        } catch (ex: MongoException) {
            System.err.println("Unable to find due to an error: $ex")
            null


        }
    }

    override suspend fun insert(collection: EnneagramFamousPeopleCollection): BsonValue? {
        try {
            val result =
                mongoDatabase.getCollection<EnneagramFamousPeopleCollection>(ENNEAGRAM_FAMOUS_PEOPLE_COLLECTION).insertOne(
                    collection
                )
            return result.insertedId
        } catch (e: MongoException) {
            System.err.println("Unable to insert due to an error: $e")
        }
        return null
    }


}