package com.example.routes.user

import com.example.di.DatabaseModule.userDao
import com.example.domain.validateUserInformation
import com.example.domain.validateUserLoginInformation
import com.example.models.User
import com.example.models.UserLogin
import com.example.models.UserUpdateModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUser(){
    patch("user/update"){
        val userUpdateData = call.receive<UserUpdateModel>()
        val userLoginData = userUpdateData.userLogin
        val updateData = userUpdateData.updatedUserData

        try{
            validateUserLoginInformation(userLoginData)
            validateUserInformation(updateData)
            if(userDao.controlUserExistenceByAuth(userLoginData)){
                userDao.updateUserByLoginInformation(userLoginData,updateData)
                call.respond(HttpStatusCode.OK, "User Updated")
            }else{
                call.respond(HttpStatusCode.NotFound, "There is no user like that")
            }


        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest, e.message.toString())

        }





    }
}

