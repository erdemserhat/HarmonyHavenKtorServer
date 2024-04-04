package com.erdemserhat.dto.responses

import kotlinx.serialization.Serializable


@Serializable
class RequestResultUUID(
    val result:Boolean,
    val message: String,
    val uuid: String,
)