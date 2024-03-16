package com.erdemserhat.models.rest.client

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    val email:String,
    val password:String
)
