package com.example.routes.user

import com.example.di.DatabaseModule.userDao
import com.example.domain.validateUserInformation
import com.example.domain.validateUserLoginInformation
import com.example.models.User
import com.example.models.UserLogin
import com.example.models.UserUpdateData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUser(){
    patch("user/update"){
        val userUpdateData = call.receive<UserUpdateData>()
        val userLoginData = userUpdateData.userLogin
        val updateData = userUpdateData.updatedUserData

        try{
            validateUserLoginInformation(userLoginData)
            validateUserInformation(updateData)
            if(userDao.controlUserExistenceByAuth(userLoginData)){
                userDao.updateUserByLoginInformation(userLoginData,updateData)
            }else{
                call.respond(HttpStatusCode.NotFound, "There is no user like that")
            }


        }catch (e:Exception){

        }





    }
}

