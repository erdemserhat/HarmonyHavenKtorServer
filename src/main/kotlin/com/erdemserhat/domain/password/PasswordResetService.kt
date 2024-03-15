package com.erdemserhat.domain.password

import com.erdemserhat.domain.email.sendPasswordResetMail
import com.erdemserhat.domain.password.PasswordResetRequests.getRequestIfExist
import com.erdemserhat.domain.password.PasswordResetRequests.isAlreadyRequested
import com.erdemserhat.domain.password.PasswordResetRequests.removeExpiredRequests
import com.erdemserhat.models.CandidatePasswordResetRequest
import com.erdemserhat.util.RandomNumberGenerator.Companion.generateRandomAuthCode
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
class PasswordResetService() {
    private var code: String = ""

    fun createRequest(email: String) {
        code = generateRandomAuthCode(6)
        removeExpiredRequests()
        if (isAlreadyRequested(email)) {
            throw Exception(
                "A active request with this email already in progress," +
                        "please wait a few minutes and try again"
            )

        } else {
            sendPasswordResetMail(email, code)
            PasswordResetRequests.addCandidateResetRequest(CandidatePasswordResetRequest(code, email))

        }


    }

    fun authenticateResponse(email: String, code: String): Boolean {
        removeExpiredRequests()
        val request = getRequestIfExist(email)
        if (request != null) {
            PasswordResetRequests.incrementAttempt(email)
            if (!request.isExpired) {
                if (request.attempts <= 3) {
                    if (request.code == code) {
                        if (!request.isPermissionUsed) {
                            PasswordResetRequests.usePermission(email)
                            return true
                        } else {
                            throw Exception("Permission was used")

                        }

                    } else {
                        throw Exception("Invalid Code")
                    }

                } else {
                    throw Exception("You entered 3 times wrong code please please create a new request for security reasons")
                }

            } else {

                throw Exception("Your request is expired, please enter the code within specified time")
            }


        } else {
            throw Exception("There is no pending request with this email, create one...")
        }


    }


}