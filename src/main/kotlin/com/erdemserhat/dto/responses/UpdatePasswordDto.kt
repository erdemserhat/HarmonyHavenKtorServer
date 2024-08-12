package com.erdemserhat.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordDto(
    val newPassword:String,
    val currentPassword:String
)
