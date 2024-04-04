package com.erdemserhat.dto.requests

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordMailerModel(
    val email:String
)
