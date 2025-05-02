package com.erdemserhat.data.database.nosql.enneagram_chart

import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class EnneagramChartCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val enneagramType: EnneagramType,
    val url: String
)