package com.erdemserhat.service.pwrservice

import com.erdemserhat.dto.responses.RequestResult

/**
 * Pool to manage password reset requests.
 */
object PasswordResetRequestsPool {
    private val requestRepository = mutableListOf<CandidatePasswordResetRequest>()

    /**
     * Adds a candidate password reset request to the pool.
     */
    fun addCandidateResetRequest(candidateRequest: CandidatePasswordResetRequest) {
        requestRepository.add(candidateRequest)
    }

    /**
     * Removes a candidate password reset request from the pool.
     */
    fun removeCandidateResetRequest(email: String) {
        requestRepository.removeIf { it.email == email }
    }

    /**
     * Checks if a password reset request exists in the pool.
     */
    fun checkPasswordResetRequestExistenceInPool(request: CandidatePasswordResetRequest): Boolean {
        return requestRepository.contains(request)
    }

    /**
     * Removes expired requests from the pool.
     */
    fun removeExpiredRequests() {
        val iterator = requestRepository.iterator()
        while (iterator.hasNext()) {
            val request = iterator.next()
            if (request.isExpired || request.attempts > 3 || request.isPermissionUsed) {
                iterator.remove()
            }
        }
    }

    /**
     * Gets a password reset request from the pool if it exists.
     */
    fun getRequestIfExist(email: String): CandidatePasswordResetRequest? {
        return requestRepository.find { it.email == email }
    }

    /**
     * Checks if a password reset request has already been made for the given email.
     */
    fun isAlreadyRequested(email: String): Boolean {
        return requestRepository.any { it.email == email }
    }

    /**
     * Retrieves the UUID of the reset request.
     */
    fun getUUIDOfResetRequest(email: String, code: String): RequestResult {
        val requestSession = requestRepository.find { it.email == email && it.code == code }
        return if (requestSession == null) {
            RequestResult(
                false,
                "Your session was expired or there is no such a session"
            )
        } else {
            RequestResult(
                false,
                requestSession.uniqueId
            )
        }
    }

    /**
     * Provides the email of the requester if the request exists.
     */
    fun provideEmailOfRequesterIfExist(uuid: String): RequestResult {
        val requestSession = requestRepository.find { it.uniqueId == uuid }
        return if (requestSession == null) {
            RequestResult(
                false,
                "Your session was expired or there is no such a session"
            )
        } else {
            RequestResult(
                true,
                requestSession.email
            )
        }
    }

    /**
     * Marks the permission as used for the request with the given email and removes expired requests.
     */
    fun usePermission(email: String) {
        requestRepository.find { it.email == email }?.usePermission()
        removeExpiredRequests()
    }
}
