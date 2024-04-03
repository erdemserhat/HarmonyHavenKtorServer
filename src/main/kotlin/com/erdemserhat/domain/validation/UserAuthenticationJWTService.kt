package com.erdemserhat.domain.validation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.erdemserhat.di.AuthenticationModule
import com.erdemserhat.di.DatabaseModule
import com.erdemserhat.models.rest.client.UserAuthenticationRequest
import com.erdemserhat.security.hashPassword
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
    fun generateJWT(): String {
        // Retrieve user's role from the database
        val userRole = DatabaseModule.userRepository.getUserByLoginInformation(userAuth.copy(password = hashPassword(userAuth.password)))!!.role

        // Create JWT token with specified claims
        val token = JWT.create()
            .withAudience(AuthenticationModule.tokenConfigSecurity.audience)
            .withIssuer(AuthenticationModule.tokenConfigSecurity.issuer)
            .withClaim("email", userAuth.email)
            .withClaim("role", userRole)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000)) // Token expiration time
            .sign(Algorithm.HMAC256(AuthenticationModule.tokenConfigSecurity.secret))

        return token
    }
}
