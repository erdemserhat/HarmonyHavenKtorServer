package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class ResetPasswordRequest(
    val email:String
)
