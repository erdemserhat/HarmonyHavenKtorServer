package com.erdemserhat.models.rest.client

import com.erdemserhat.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateModel(
    val userLogin: UserLogin,
    val updatedUserData: User
)

