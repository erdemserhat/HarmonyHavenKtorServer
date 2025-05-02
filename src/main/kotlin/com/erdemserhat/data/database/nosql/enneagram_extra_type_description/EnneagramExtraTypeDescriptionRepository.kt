package com.erdemserhat.data.database.nosql.enneagram_extra_type_description

import com.erdemserhat.data.database.nosql.EnneagramExtraTypeDescription
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory
import org.bson.BsonValue

interface EnneagramExtraTypeDescriptionRepository {
    suspend fun getExtraTypeDescriptionByTypeAndCategory(type: Int,category: EnneagramExtraTypeDescriptionCategory): EnneagramExtraTypeDescriptionCollection?
    suspend fun addExtraTypeDescription(extraTypeDescription: EnneagramExtraTypeDescriptionCollection):BsonValue?
}