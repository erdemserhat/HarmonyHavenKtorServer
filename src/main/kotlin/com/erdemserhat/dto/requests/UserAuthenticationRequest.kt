package com.erdemserhat.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthenticationRequest(
    val email:String,
    val password:String
)
