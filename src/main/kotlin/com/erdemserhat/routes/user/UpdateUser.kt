package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.validateIfEmailChanged
import com.erdemserhat.domain.validation.validateUserInformation
import com.erdemserhat.domain.validation.validateUserLoginInformation
import com.erdemserhat.models.UserUpdateModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUser(){
    patch("/user/update"){
        val userUpdateData = call.receive<UserUpdateModel>()
        val userLoginData = userUpdateData.userLogin
        val updateData = userUpdateData.updatedUserData

        try{
            validateUserLoginInformation(userLoginData)
            validateUserInformation(user = updateData, validateExistence = false)
            validateIfEmailChanged(userLoginData,updateData)
            if(userRepository.controlUserExistenceByAuth(userLoginData)){
                userRepository.updateUserByLoginInformation(userLoginData,updateData)
                call.respond(HttpStatusCode.OK, "User Updated")
            }else{
                call.respond(HttpStatusCode.NotFound, "There is no user like that")
            }


        }catch (e:Exception){
            call.respond(HttpStatusCode.BadRequest, e.message.toString())

        }





    }
}

