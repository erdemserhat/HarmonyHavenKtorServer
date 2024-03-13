package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class ForgotPasswordMailerModel(
    val email:String
)
