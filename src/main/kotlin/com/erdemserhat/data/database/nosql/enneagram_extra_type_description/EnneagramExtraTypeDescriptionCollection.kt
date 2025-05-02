package com.erdemserhat.data.database.nosql.enneagram_extra_type_description

import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class EnneagramExtraTypeDescriptionCollection(
    @BsonId val id: ObjectId = ObjectId(),
    val extraType: Int,
    val description: String,
    val category: EnneagramExtraTypeDescriptionCategory,

    )
