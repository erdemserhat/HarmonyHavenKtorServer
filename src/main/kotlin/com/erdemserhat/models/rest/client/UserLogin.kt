package com.erdemserhat.models.rest.client

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthenticationRequest(
    val email:String,
    val password:String
)
