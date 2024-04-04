package com.erdemserhat.dto.requests

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordAuthModel(
    val code:String,
    val email:String
)
