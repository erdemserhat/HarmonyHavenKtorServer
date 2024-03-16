package com.erdemserhat.models.rest.client

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordAuthModel(
    val code:String,
    val email:String
)
