package com.erdemserhat.domain.password

import com.erdemserhat.models.rest.server.RequestResult

object PasswordResetRequestsPool {
    private val requestRepository = mutableListOf<CandidatePasswordResetRequest>()


    fun addCandidateResetRequest(candidateRequest: CandidatePasswordResetRequest) {

        requestRepository.add(candidateRequest)

    }

    fun removeCandidateResetRequest(email: String) {
        requestRepository.removeIf { it.email == email }
    }

    fun checkPasswordResetRequestExistenceInPool(request: CandidatePasswordResetRequest): Boolean {
        return requestRepository.contains(request)
    }

    fun removeExpiredRequests() {
        val iterator = requestRepository.iterator()
        while (iterator.hasNext()) {
            val request = iterator.next()
            if (request.isExpired || request.attempts > 3 || request.isPermissionUsed) {
                iterator.remove() //remove the invalid request to avoid overloading request repository
            }
        }
    }


    fun getRequestIfExist(email: String): CandidatePasswordResetRequest? {
        val request = requestRepository.find { it.email == email }

        return request


    }


    fun isAlreadyRequested(email: String): Boolean {
        val tempRequest = requestRepository.find { it.email == email }
        return tempRequest != null

    }

    fun getUUIDOfResetRequest(email: String, code: String): RequestResult {
        val requestSession = requestRepository.find { it.email == email && it.code == code }
        if (requestSession == null) {
            return RequestResult(
                false,
                "Your session was expired or there is no such a session"
            )
        }

        return RequestResult(
            false,
            requestSession.uniqueId
        )


    }

    fun provideEmailOfRequesterIfExist(uuid: String): RequestResult {
        val requestSession = requestRepository.find { it.uniqueId == uuid }
        if (requestSession == null) {
            return RequestResult(
                false,
                "Your session was expired or there is no such a session"
            )


        }
        return RequestResult(
            true,
            requestSession.email
        )

    }

    fun usePermission(email: String) {
        requestRepository.find { it.email == email }?.usePermission()
        removeExpiredRequests()
    }


}