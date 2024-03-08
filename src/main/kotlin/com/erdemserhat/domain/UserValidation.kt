package com.erdemserhat.domain

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.models.User

fun validateUserInformation(
    user: User,
    validateInfo: Boolean = true,
    validateExistence: Boolean = true
) {
    if (validateInfo) validateForm(user)
    if (validateExistence) validateExistence(user)

}

private fun validateForm(user: User) {
    val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}")
    // General Validation
    if (user.name.isEmpty()) {
        throw Exception("Name cannot be empty")
    } else if (user.surname.isEmpty()) {
        throw Exception("Surname cannot be empty")
    } else if (user.password.length < 8) {
        throw Exception("Password must be at least 8 characters long")
    } else if (user.password.contains(user.name, true) || user.password.contains(user.surname, true)) {
        throw Exception("Password must not contain personal information")
    } else if (user.password.contains(user.email, true) || user.password == user.email) {
        throw Exception("Password must not contain email information")
    } else if (!user.password.matches(passwordRegex)) {
        throw Exception("Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long")
    }

}

fun validateExistence(user: User) {
    // Duplicate User Control
    if (userRepository.controlUserExistenceByEmail(user.email)) {
        throw Exception("A user with this email already exists")
    }
}
