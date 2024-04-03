package com.erdemserhat.domain.password
import kotlinx.coroutines.*
import java.util.*

/**
 * Data class representing a candidate password reset request.
 * @property code The unique code associated with the password reset request.
 * @property email The email address associated with the password reset request.
 * @property isExpired A flag indicating whether the password reset request has expired.
 * @property attempts The number of attempts made to verify the password reset request.
 * @property uniqueId A unique identifier for the password reset request.
 * @property isPermissionUsed A flag indicating whether the permission to reset the password has been used.
 */
@OptIn(DelicateCoroutinesApi::class)
data class CandidatePasswordResetRequest(
    val code: String,
    val email: String,
    var isExpired: Boolean = false,
    var attempts: Int = 0,
    val uniqueId: String = UUID.randomUUID().toString(),
    var isPermissionUsed: Boolean = false
) {
    init {
        // Set up a coroutine to mark the request as expired after 180,000 milliseconds (3 minutes)
        GlobalScope.launch(Dispatchers.Default) {
            delay(180_000)
            isExpired = true
        }


    }

    /**
     * Increment the number of attempts made to verify the password reset request.
     */
    fun incrementAttempt() {
        attempts++

    }

    /**
     * Mark the permission to reset the password as used.
     */
    fun usePermission() {
        isPermissionUsed = true
    }
}
