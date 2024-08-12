package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class Quote(
    val id:Int,
    val quote:String,
    val writer:String,
)
