package com.erdemserhat.service.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.erdemserhat.EncryptionDataDto
import com.erdemserhat.EncryptionToFarawayServerModel
import com.erdemserhat.service.di.AuthenticationModule
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.dto.requests.UserAuthenticationRequest
import java.util.*

/**
 * Provides services to generate JWT tokens for user authentication.
 * This class generates JWT tokens based on the provided user authentication request.
 * @property userAuth The user authentication request data.
 */
class UserAuthenticationJWTService(
    private val userAuth: UserAuthenticationRequest
) {
    /**
     * Generates a JWT token for the authenticated user.
     * @return A string representing the generated JWT token.
     */
    suspend fun generateJWT(): String {
        // Retrieve user's role from the database
       // val hashedPassword = makeEncryptionRequest(
       //     EncryptionToFarawayServerModel(
         //       encryptionData = EncryptionDataDto(
            //        sensitiveData = userAuth.password,
             ///       userUUID = DatabaseModule.userRepository.getUserByEmailInformation(userAuth.email)!!.uuid
             //   )

          //  )
       // )

        val hashedPassword = hashPassword(userAuth.password)

        val userRole = DatabaseModule.userRepository.getUserByLoginInformation(userAuth.copy(password = hashedPassword))!!.role

        // Create JWT token with specified claims
        val token = JWT.create()
            .withAudience(AuthenticationModule.tokenConfigSecurity.audience)
            .withIssuer(AuthenticationModule.tokenConfigSecurity.issuer)
            .withClaim("email", userAuth.email)
            .withClaim("role", userRole)
            .withExpiresAt(Date(System.currentTimeMillis() + 600000000000)) // Token expiration time
            .sign(Algorithm.HMAC256(AuthenticationModule.tokenConfigSecurity.secret))

        return token
    }
}
