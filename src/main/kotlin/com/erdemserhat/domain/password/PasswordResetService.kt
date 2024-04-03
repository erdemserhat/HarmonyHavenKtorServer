package com.erdemserhat.domain.password

import com.erdemserhat.domain.mailservice.sendPasswordResetMail

import com.erdemserhat.models.rest.server.RequestResult
import com.erdemserhat.util.RandomNumberGenerator.Companion.generateRandomAuthCode
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
class PasswordResetService() {
    private var code: String = ""

    fun createRequest(email: String): RequestResult {
        code = generateRandomAuthCode(6)
        PasswordResetRequestsPool.removeExpiredRequests()
        val isAlreadyRequested = PasswordResetRequestsPool.isAlreadyRequested(email)

        if (isAlreadyRequested) {
            return RequestResult(
                false,
                "A active request with this email already in progress," +
                        "please wait a few minutes and try again"
            )

        }
        sendPasswordResetMail(email, code)
        PasswordResetRequestsPool.addCandidateResetRequest(CandidatePasswordResetRequest(code, email))
        return RequestResult(
            true,
            "Email is sent to $email address."
        )


    }

    fun authenticateRequest(email: String, code: String): RequestResult {
        val claim = PasswordResetRequestsPool.getRequestIfExist(email)

        if(claim==null)
            return RequestResult(
                false,
                message = "There is no pending request with this email, create one."
            )


        if(claim.isExpired)
            return RequestResult(
                false,
                message = "Your request is expired, please enter the code within specified time"
            )



        if(claim.code != code) {
            claim.incrementAttempt()
            if(claim.attempts>3) {
                PasswordResetRequestsPool.removeExpiredRequests()
                return RequestResult(
                    false,
                    message = "You entered 3 times wrong code please please create a new request for security reasons"
                )
            }

            return RequestResult(
                false,
                message = "Invalid Code"
            )

        }


        return RequestResult(true)


    }


}