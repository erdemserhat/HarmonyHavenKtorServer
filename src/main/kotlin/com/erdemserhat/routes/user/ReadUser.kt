package com.erdemserhat.routes.user
import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.validateUserLoginInformation
import com.erdemserhat.models.rest.server.RequestResult
import com.erdemserhat.models.rest.client.UserLogin
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.readUser(){
    post("user/login"){
        val userLoginInformation = call.receive<UserLogin>()
        try{
            validateUserLoginInformation(userLoginInformation)
            val user = userRepository.getUserByLoginInformation(userLoginInformation)
            if(user!=null){
                call.respond(RequestResult(true,"Welcome, ${user.name}"))
            }else{
                call.respond(RequestResult(false,"There is no user like that in database"))

            }
        }catch (e:Exception){
            call.respond(RequestResult(false,e.message.toString()))
        }

    }
}

