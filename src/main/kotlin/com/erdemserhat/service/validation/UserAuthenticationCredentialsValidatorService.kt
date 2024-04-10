package com.erdemserhat.service.authentication

import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.validation.ValidationResult
import com.erdemserhat.dto.requests.UserAuthenticationRequest
import com.erdemserhat.service.security.hashPassword

/**
 * Provides authentication services for user login requests.
 * This class verifies user credentials against the stored data in the database.
 * @property userAuthRequest The user authentication request data.
 */
class UserAuthenticationCredentialsValidatorService(
    private val userAuthRequest: UserAuthenticationRequest
) {
    /**
     * Authenticates the user based on the provided authentication request.
     * @return A ValidationResult object indicating the result of the authentication process.
     */
    fun verifyUser(): ValidationResult {
        // Check if a user with the provided email exists in the database
        val userExists = DatabaseModule.userRepository.controlUserExistenceByEmail(userAuthRequest.email)
        if (!userExists) {
            return ValidationResult(
                false,
                "There is no user with this email. Please register.",
                errorCode = 104
            )
        }

        // Check if the provided password matches the stored password for the user
        val hashedPassword = hashPassword(userAuthRequest.password)
        val isValidPassword = DatabaseModule.userRepository.controlUserExistenceByAuth(userAuthRequest.copy(password = hashedPassword))
        if (!isValidPassword) {
            return ValidationResult(
                false,
                "Incorrect password.",
                errorCode = 105
            )
        }

        // Authentication successful
        return ValidationResult(true)
    }
}
