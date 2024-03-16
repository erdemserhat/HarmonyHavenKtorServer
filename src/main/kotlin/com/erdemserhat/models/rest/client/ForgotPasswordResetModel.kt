package com.erdemserhat.models.rest.client

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordResetModel(
    val uuid:String,
    val password:String
)