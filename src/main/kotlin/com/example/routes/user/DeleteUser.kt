package com.example.routes.user

import com.example.di.DatabaseModule.userDao
import com.example.domain.validateUserLoginInformation
import com.example.models.UserLogin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUser(){
    delete("user/delete"){
        val userInformation = call.receive<UserLogin>()
        try{
            validateUserLoginInformation(userInformation)
            if(userDao.deleteUserByLoginInformation(userInformation)){
                call.respond(HttpStatusCode.OK,"You have successfully deleted your account")

            }else{
                call.respond(HttpStatusCode.NotFound,"There is no user like that in database")

            }

        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest,e.message.toString())
        }
    }

}

