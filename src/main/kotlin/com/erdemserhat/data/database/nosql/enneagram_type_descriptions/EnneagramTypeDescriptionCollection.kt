package com.erdemserhat.data.database.nosql.enneagram_type_descriptions

import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class EnneagramTypeDescriptionCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val fullDescriptionCode: Int,
    val enneagramType: EnneagramType,
    val enneagramTypeDescriptionCategory: EnneagramTypeDescriptionCategory,
    val description: String,
)
