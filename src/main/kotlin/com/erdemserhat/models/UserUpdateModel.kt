package com.erdemserhat.models

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateModel(
    val userLogin: UserLogin,
    val updatedUserData: User
)

