package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class RequestResult(
    val result:Boolean,
    val message:String
)
