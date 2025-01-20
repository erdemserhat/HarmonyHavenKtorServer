package com.erdemserhat.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.erdemserhat.service.di.AuthenticationModule.tokenConfigSecurity
import com.erdemserhat.service.security.token.TokenConfig
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

/**
 * Configures security for the application using JWT authentication.
 * This function installs JWT authentication and configures it with the provided TokenConfig.
 */
fun Application.configureSecurity(config: TokenConfig) {
    install(Authentication) {
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            authHeader { call ->
                // 1. Authorization header'ı kontrol et
                call.request.parseAuthorizationHeader()
                    ?: run {
                        // 2. Authorization header yoksa cookie'yi kontrol et
                        val cookieValue = call.request.cookies["auth_token"] ?: return@authHeader null
                        parseAuthorizationHeader("Bearer $cookieValue")
                    }
            }
            validate { credential ->
                if (credential.payload.audience.contains(config.audience)) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}

/**
 * Configures the token configuration for the application.
 * This function reads token configuration from environment properties and initializes the TokenConfig.
 */
fun Application.configureTokenConfig() {
    // Read token configuration from environment properties
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    // Create a TokenConfig with the read configuration
    val tokenConfig = TokenConfig(issuer, audience, Long.MAX_VALUE, secret)

    // Assign the TokenConfig to the global tokenConfigSecurity variable
    tokenConfigSecurity = tokenConfig
}
