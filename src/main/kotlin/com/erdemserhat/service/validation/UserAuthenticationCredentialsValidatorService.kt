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
    suspend fun verifyUser(): ValidationResult {
        // Check if a user with the provided email exists in the database
        val userExists = DatabaseModule.userRepository.controlUserExistenceByEmail(userAuthRequest.email)
        if (!userExists) {
            return ValidationResult(
                false,
                "There is no user with this email. Please register.",
                errorCode = 104
            )
        }


//////////////////////////////////////////////// DELEGATION POINT /////////////////////////////////////////////////////////////////////////////
        // Check if the provided password matches the stored password for the user
        val hashedPassword = hashPassword(userAuthRequest.password)
        val RED = "\u001B[31m"
        val GREEN = "\u001B[32m"
        val YELLOW = "\u001B[33m"
        val RESET = "\u001B[0m"

        /*

        println("${YELLOW}--Encrption is requested by Main Server-->->->")
        val hashedPassword = makeEncryptionRequest(
            EncryptionToFarawayServerModel(
                encryptionData = EncryptionDataDto(
                    sensitiveData = userAuthRequest.password,
                    userUUID = DatabaseModule.userRepository.getUserByEmailInformation(userAuthRequest.email)!!.uuid
                )

            )
        )
        */

//////////////////////////////////////////// DELEGATION POINT /////////////////////////////////////////////////////////////////////////////
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
