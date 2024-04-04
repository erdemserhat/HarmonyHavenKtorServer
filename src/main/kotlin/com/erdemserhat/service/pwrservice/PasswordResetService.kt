package com.erdemserhat.service.pwrservice

import com.erdemserhat.romote.mail.sendPasswordResetMail
import com.erdemserhat.dto.responses.RequestResult
import com.erdemserhat.util.RandomNumberGenerator.Companion.generateRandomAuthCode
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * Service responsible for password reset functionality.
 */
@OptIn(DelicateCoroutinesApi::class)
class PasswordResetService {

    private var code: String = ""

    /**
     * Creates a password reset request for the given email.
     */
    fun createRequest(email: String): RequestResult {
        // Generate a random authentication code
        code = generateRandomAuthCode(6)

        // Remove expired requests from the pool
        PasswordResetRequestsPool.removeExpiredRequests()

        // Check if there is already an active request with this email
        val isAlreadyRequested = PasswordResetRequestsPool.isAlreadyRequested(email)
        if (isAlreadyRequested) {
            return RequestResult(
                false,
                "An active request with this email is already in progress. " +
                        "Please wait a few minutes and try again."
            )
        }

        // Send password reset mail
        sendPasswordResetMail(email, code)

        // Add the candidate reset request to the pool
        PasswordResetRequestsPool.addCandidateResetRequest(CandidatePasswordResetRequest(code, email))

        return RequestResult(
            true,
            "An email has been sent to $email address."
        )
    }

    /**
     * Authenticates a password reset request.
     */
    fun authenticateRequest(email: String, code: String): RequestResult {
        // Get the reset request from the pool
        val claim = PasswordResetRequestsPool.getRequestIfExist(email)

        // If there is no pending request with this email, return an error
        if (claim == null) {
            return RequestResult(
                false,
                message = "There is no pending request with this email. Please create one."
            )
        }

        // If the request is expired, return an error
        if (claim.isExpired) {
            return RequestResult(
                false,
                message = "Your request is expired. Please enter the code within the specified time."
            )
        }

        // If the code is invalid, increment the attempt count and return an error
        if (claim.code != code) {
            claim.incrementAttempt()
            if (claim.attempts > 3) {
                PasswordResetRequestsPool.removeExpiredRequests()
                return RequestResult(
                    false,
                    message = "You have entered the wrong code 3 times. " +
                            "Please create a new request for security reasons."
                )
            }
            return RequestResult(
                false,
                message = "Invalid code."
            )
        }

        // If authentication is successful, return success
        return RequestResult(true)
    }
}
