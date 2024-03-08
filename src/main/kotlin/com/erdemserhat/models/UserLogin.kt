package com.erdemserhat.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    val email:String,
    val password:String
)
