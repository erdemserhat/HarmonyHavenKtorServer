package com.erdemserhat.models

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
data class CandidatePasswordResetRequest(
    val code:String,
    val email:String,
    var isExpired:Boolean = false,
    var attempts:Int =0,
    val uniqueId: String = UUID.randomUUID().toString(),
    var isPermissionUsed:Boolean = false
){
    init {
        GlobalScope.launch{
            delay(180_000)
                isExpired=true


        }

    }

    fun incrementAttempt(){
        attempts++
    }

    fun usePermission(){
        isPermissionUsed = true
    }


}
