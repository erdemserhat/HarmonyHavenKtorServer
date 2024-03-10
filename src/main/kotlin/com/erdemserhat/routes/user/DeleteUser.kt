package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.validateUserLoginInformation
import com.erdemserhat.models.RequestResult
import com.erdemserhat.models.UserLogin
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUser(){
    patch("user/delete"){
        val userInformation = call.receive<UserLogin>()
        try{
            validateUserLoginInformation(userInformation)
            if(userRepository.deleteUserByLoginInformation(userInformation)){
                call.respond(RequestResult(true,"You have successfully deleted your account"))

            }else{
                call.respond(RequestResult(false,"There is no user like that in database"))

            }

        }catch (e:Exception){
            call.respond(RequestResult(false,e.message.toString()))
        }
    }

}

