package com.erdemserhat.routes.user
import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.validateUserLoginInformation
import com.erdemserhat.models.UserLogin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.readUser(){
    get("user/info"){
        val userLoginInformation = call.receive<UserLogin>()
        try{
            validateUserLoginInformation(userLoginInformation)
            val user = userRepository.getUserByLoginInformation(userLoginInformation)
            if(user!=null){
                call.respond(HttpStatusCode.OK,user)
            }else{
                call.respond(HttpStatusCode.NotFound,"There is no user like that in database")

            }
        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest,e.message.toString())
        }

    }
}

