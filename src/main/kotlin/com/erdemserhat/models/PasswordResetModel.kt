package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class PasswordResetModel(
    val code:String,
    val newPassword:String
)
