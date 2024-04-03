package com.erdemserhat.domain.validation

import kotlinx.serialization.Serializable

/**
 * Serializable data class representing the result of a validation operation.
 * @property isValid Indicates whether the validation result is valid or not.
 * @property errorMessage Contains the error message if the validation result is not valid.
 * @property errorCode Contains the error code associated with the validation result, if any.
 */
@Serializable
data class ValidationResult(
    val isValid: Boolean = true,
    val errorMessage: String = "",
    val errorCode: Int = 0
)

/**
 * Validates the format of an email address.
 * @param email The email address to validate.
 * @return true if the email address format is valid, false otherwise.
 */
fun isValidEmailFormat(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    return email.matches(emailRegex)
}

/**
 * Validates the length of a password.
 * @param password The password to validate.
 * @return true if the password length is at least 8 characters, false otherwise.
 */
fun validatePasswordLength(password: String): Boolean {
    return password.length >= 8
}

/**
 * Validates the strength of a password based on certain criteria.
 * @param password The password to validate.
 * @return true if the password meets the strength criteria, false otherwise.
 */
fun validatePasswordStrength(password: String): Boolean {
    val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}")
    return passwordRegex.matches(password)
}
