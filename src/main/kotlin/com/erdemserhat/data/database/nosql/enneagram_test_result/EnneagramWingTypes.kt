package com.erdemserhat.data.database.nosql.enneagram_test_result

import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import kotlinx.serialization.Serializable


@Serializable
data class EnneagramWingTypes(
    val pointBasedWingType:Int,
    val enneagramBasedWingType:Int,
)
