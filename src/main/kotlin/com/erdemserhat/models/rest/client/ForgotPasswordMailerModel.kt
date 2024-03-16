package com.erdemserhat.models.rest.client

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordMailerModel(
    val email:String
)
