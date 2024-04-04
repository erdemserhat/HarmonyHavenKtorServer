package com.erdemserhat.dto.responses

import com.erdemserhat.service.validation.ValidationResult
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val formValidationResult: ValidationResult,
    val credentialsValidationResult: ValidationResult?,
    val isAuthenticated: Boolean,
    val jwt: String?,
)

