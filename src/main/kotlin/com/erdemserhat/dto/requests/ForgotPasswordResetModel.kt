package com.erdemserhat.dto.requests

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordResetModel(
    val uuid:String,
    val password:String
)