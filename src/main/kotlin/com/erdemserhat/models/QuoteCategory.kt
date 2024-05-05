package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class QuoteCategory(
    val id:Int=0,
    val name:String
)
