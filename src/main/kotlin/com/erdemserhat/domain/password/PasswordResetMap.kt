package com.erdemserhat.domain.password

import com.erdemserhat.models.CandidatePasswordResetRequest

object PasswordResetRequests {
    private val requestRepository = mutableListOf<CandidatePasswordResetRequest>()


    fun addCandidateResetRequest(candidateRequest: CandidatePasswordResetRequest) {

        requestRepository.add(candidateRequest)

    }

    fun removeCandidateResetRequest(email:String) {
        requestRepository.removeIf { it.email==email }
    }

    fun authenticateCandidateRequest(response: CandidatePasswordResetRequest): Boolean {
        return requestRepository.contains(response)
    }

    fun removeExpiredRequests() {
        for (request in requestRepository) {
            requestRepository.removeIf { it.isExpired || it.attempts>3 }
        }
    }

    fun getRequestIfExist(email: String): CandidatePasswordResetRequest? {

        val request = requestRepository.find { it.email==email }

        return request


    }

    fun incrementAttempt(email:String){
        for (request in requestRepository){
            if(request.email==email){
                request.attempts++
            }
        }
    }

    fun isAlreadyRequested(email: String):Boolean{
        removeExpiredRequests()
        val tempRequest = requestRepository.find { it.email==email }
        return tempRequest != null

    }

    fun getUUIDOfResetRequest(email: String,code:String):String{
       val requestSession= requestRepository.find { it.email==email && it.code==code }
        if(requestSession==null){
            throw Exception("1Your session was expired or there is no such a session")
        }else{
            return requestSession.uniqueId
        }

    }

    fun provideEmailOfRequesterIfExist(uuid:String):String{
        val requestSession = requestRepository.find { it.uniqueId==uuid }
        if(requestSession==null){
            throw Exception("2Your session was expired or there is no such a session")

        }else{
            return requestSession.email
        }

    }

    fun usePermission(email:String){
        for (request in requestRepository){
            if(request.email==email){
                request.usePermission()
            }
        }
    }


}