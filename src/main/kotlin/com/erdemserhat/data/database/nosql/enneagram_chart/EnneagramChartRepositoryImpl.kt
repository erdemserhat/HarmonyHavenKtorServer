package com.erdemserhat.data.database.nosql.enneagram_chart

import com.erdemserhat.data.database.nosql.MongoDatabaseConfig
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import org.bson.BsonValue
import org.bson.codecs.pojo.annotations.BsonId

class EnneagramChartRepositoryImpl(
    private val mongoDatabase: MongoDatabase = MongoDatabaseConfig.mongoDatabase
):EnneagramChartRepository {

    companion object {
        const val ENNEAGRAM_FAMOUS_PEOPLE_COLLECTION = "enneagram_charts"
    }


    override suspend fun getChartByType(enneagramType: EnneagramType): EnneagramChartCollection? {
        val chart = mongoDatabase
            .getCollection<EnneagramChartCollection>(ENNEAGRAM_FAMOUS_PEOPLE_COLLECTION)
            .find(Filters.eq("enneagramType", enneagramType))
            .firstOrNull()

        return chart
    }

    override suspend fun insertChart(chart: EnneagramChartCollection): BsonValue? {
        try {
            val result = mongoDatabase.getCollection<EnneagramChartCollection>(ENNEAGRAM_FAMOUS_PEOPLE_COLLECTION)
                .insertOne(chart)

            return result.insertedId

        }catch (e:Exception){
            System.err.println(e)
        }
        return null

    }

}