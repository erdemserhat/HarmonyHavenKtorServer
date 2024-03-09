package com.erdemserhat.domain.validation

import com.erdemserhat.models.UserLogin

fun validateUserLoginInformation(login: UserLogin){
    validateEmailFormat(login.email)
    validatePasswordFormat(login.password)

}

fun validateEmailFormat(email: String) {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    if (!email.matches(emailRegex)) {
            throw IllegalArgumentException("Invalid email format")
    }

}

fun validatePasswordFormat(password: String) {
    val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}")
    if (!password.matches(passwordRegex)) {
        throw IllegalArgumentException("Invalid password format. Password must contain at least one uppercase letter, one lowercase letter, and one digit, and be at least 8 characters long.")
    }
}
