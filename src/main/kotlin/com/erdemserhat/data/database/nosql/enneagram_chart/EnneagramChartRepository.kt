package com.erdemserhat.data.database.nosql.enneagram_chart

import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import org.bson.BsonValue
import org.bson.codecs.pojo.annotations.BsonId

interface EnneagramChartRepository {
    suspend fun getChartByType(enneagramType: EnneagramType):EnneagramChartCollection?
    suspend fun insertChart(chart: EnneagramChartCollection): BsonValue?
}