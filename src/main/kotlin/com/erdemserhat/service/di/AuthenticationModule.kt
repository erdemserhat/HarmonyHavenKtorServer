package com.erdemserhat.service.di

import com.erdemserhat.service.security.token.TokenConfig

/**
 * Object responsible for managing authentication-related dependencies.
 */
object AuthenticationModule {
    /**
     * Configuration for token-based authentication.
     */
    var tokenConfigSecurity = TokenConfig("", "", 0, "")
}
