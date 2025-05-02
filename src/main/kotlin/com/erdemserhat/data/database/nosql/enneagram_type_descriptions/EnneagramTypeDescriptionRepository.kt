package com.erdemserhat.data.database.nosql.enneagram_type_descriptions

import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import org.bson.BsonValue

interface EnneagramTypeDescriptionRepository {
    suspend fun getDescriptionByTypeAndCategory(
        type: EnneagramType,
        category: EnneagramTypeDescriptionCategory
    ):EnneagramTypeDescriptionCollection?


    suspend fun addEnneagramTypeDescription(enneagramTypeDescriptionCollection: EnneagramTypeDescriptionCollection):BsonValue?
}