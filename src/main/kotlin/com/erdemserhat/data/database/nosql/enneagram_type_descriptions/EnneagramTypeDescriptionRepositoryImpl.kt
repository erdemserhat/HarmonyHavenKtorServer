package com.erdemserhat.data.database.nosql.enneagram_type_descriptions

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import org.bson.BsonDocument
import org.bson.BsonValue

class EnneagramTypeDescriptionRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase,
):EnneagramTypeDescriptionRepository {

    companion object {
        const val ENNEAGRAM_TYPE_DESCRIPTION = "enneagram_type_descriptions"
    }

    override suspend fun getDescriptionByTypeAndCategory(
        type: EnneagramType,
        category: EnneagramTypeDescriptionCategory
    ): EnneagramTypeDescriptionCollection? {

        val descriptions = mongoDatabase.getCollection<EnneagramTypeDescriptionCollection>(ENNEAGRAM_TYPE_DESCRIPTION)
            .find(Filters.and(
                Filters.eq("enneagramType", type),
                Filters.eq("enneagramTypeDescriptionCategory", category)
            )).firstOrNull()
        return descriptions


    }

    override suspend fun addEnneagramTypeDescription(enneagramTypeDescriptionCollection: EnneagramTypeDescriptionCollection): BsonValue? {
       return try {
           val result =  mongoDatabase.getCollection<EnneagramTypeDescriptionCollection>(ENNEAGRAM_TYPE_DESCRIPTION)
                .insertOne(enneagramTypeDescriptionCollection)

            return result.insertedId

        }catch (e: Exception) {
            System.err.println(e.message)
           null
        }

    }
}