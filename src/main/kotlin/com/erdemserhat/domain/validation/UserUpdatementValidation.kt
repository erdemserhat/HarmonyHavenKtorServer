package com.erdemserhat.domain.validation

import com.erdemserhat.models.User
import com.erdemserhat.models.UserLogin

fun validateIfEmailChanged(login: UserLogin, updatedUser: User) {
    if (login.email != updatedUser.email) {
        throw Exception("You cannot change your email due to security policies. Please contact us for assistance.")
    }
}
