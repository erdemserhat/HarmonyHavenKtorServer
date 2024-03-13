package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordResetModel(
    val uuid:String,
    val password:String
)