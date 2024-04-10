package com.erdemserhat.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetResponse(
    val errorCode:Int,
    val errorMessage:String,
    val uuid:String
)
