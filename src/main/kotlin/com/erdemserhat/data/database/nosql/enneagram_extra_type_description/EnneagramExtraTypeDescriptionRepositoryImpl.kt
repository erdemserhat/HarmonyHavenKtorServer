package com.erdemserhat.data.database.nosql.enneagram_extra_type_description

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCollection
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionRepository
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionRepositoryImpl.Companion.ENNEAGRAM_TYPE_DESCRIPTION
import com.erdemserhat.service.di.EnneagramRepositoryModule
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import org.bson.BsonValue

class EnneagramExtraTypeDescriptionRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase,
): EnneagramExtraTypeDescriptionRepository {

    companion object {
        const val ENNEAGRAM_EXTRA_TYPE_DESCRIPTION = "enneagram_extra_type_description"
    }

    override suspend fun getExtraTypeDescriptionByTypeAndCategory(
        type: Int,
        category: EnneagramExtraTypeDescriptionCategory
    ): EnneagramExtraTypeDescriptionCollection? {
        val descriptions = mongoDatabase.getCollection<EnneagramExtraTypeDescriptionCollection>(ENNEAGRAM_EXTRA_TYPE_DESCRIPTION)
            .find(Filters.and(
                Filters.eq("extraType", type),
                Filters.eq("category", category)
            )).firstOrNull()
        return descriptions
    }

    override suspend fun addExtraTypeDescription(extraTypeDescription: EnneagramExtraTypeDescriptionCollection): BsonValue? {
        return try {
            val result =  mongoDatabase.getCollection<EnneagramExtraTypeDescriptionCollection>(ENNEAGRAM_EXTRA_TYPE_DESCRIPTION)
                .insertOne(extraTypeDescription)

            return result.insertedId

        }catch (e: Exception) {
            System.err.println(e.message)
            null
        }
    }


}