package com.erdemserhat.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthenticationRequest(
    val googleIdToken: String,
)
