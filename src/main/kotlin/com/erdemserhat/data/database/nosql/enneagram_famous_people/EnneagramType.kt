package com.erdemserhat.data.database.nosql.enneagram_famous_people

import kotlinx.serialization.Serializable


@Serializable
data class EnneagramType(
    val dominantType: Int,
    val wingType: Int
)
