package com.erdemserhat.domain.authentication

import com.erdemserhat.di.DatabaseModule
import com.erdemserhat.domain.validation.ValidationResult
import com.erdemserhat.models.rest.client.UserAuthenticationRequest
import com.erdemserhat.security.hashPassword
import kotlinx.serialization.Serializable

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
            return ValidationResult(false, "There is no user with this email. Please register.")
        }

        // Check if the provided password matches the stored password for the user
        val hashedPassword = hashPassword(userAuthRequest.password)
        val isValidPassword = DatabaseModule.userRepository.controlUserExistenceByAuth(userAuthRequest.copy(password = hashedPassword))
        if (!isValidPassword) {
            return ValidationResult(false, "Incorrect password.")
        }

        // Authentication successful
        return ValidationResult(true)
    }
}
