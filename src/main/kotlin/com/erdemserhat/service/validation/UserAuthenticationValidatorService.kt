package com.erdemserhat.service.validation

import com.erdemserhat.dto.requests.UserAuthenticationRequest

/**
 * This class provides validation services for user authentication data.
 * It validates user email format and password strength.
 * @property user The user authentication data to be validated.
 */
class UserAuthenticationInputValidatorService(
    private val user: UserAuthenticationRequest
) {
    /**
     * Validates the user authentication form.
     * @return ValidationResult indicating whether the authentication form is valid or not.
     */
    fun validateAuthenticationForm(): ValidationResult {
        // Validate email format
        if (!isValidEmailFormat(user.email))
            return ValidationResult(
                false,
                "Invalid email format.",
                errorCode = 101
            )

        // Validate password length
        if (!validatePasswordLength(user.password))
            return ValidationResult(
                false, "Password must be at least 8 characters long.",
                errorCode = 102
            )

        // Validate password strength
        if (!validatePasswordStrength(user.password))
            return ValidationResult(
                false,
                "Password must contain at least one uppercase letter, one lowercase letter, and one digit.",
                errorCode = 103
            )

        return ValidationResult(true)
    }

    fun validatePassword(): ValidationResult {
        // Validate password length
        if (!validatePasswordLength(user.password))
            return ValidationResult(
                false,
                "Password must be at least 8 characters long."
            )

        // Validate password strength
        if (!validatePasswordStrength(user.password))
            return ValidationResult(
                false,
                "Password must contain at least one uppercase letter, one lowercase letter, and one digit."
            )

        return ValidationResult(true)
    }
}
