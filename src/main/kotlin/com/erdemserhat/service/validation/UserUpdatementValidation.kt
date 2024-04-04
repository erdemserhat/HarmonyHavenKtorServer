package com.erdemserhat.service.validation

fun validateIfEmailChanged(email:String, updatedEmail:String):ValidationResult {
    if (email != updatedEmail) {
        return ValidationResult(
            false,
            "You cannot change your email because of security policy...."
        )
    }

    return ValidationResult(true)
}
