package com.erdemserhat.models.rest.server

import kotlinx.serialization.Serializable


@Serializable
open class RequestResult(
    open val result:Boolean,
    open val message:String
)
