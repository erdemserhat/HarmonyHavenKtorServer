package com.example.routes.user

import com.example.di.DatabaseModule.userDao
import com.example.domain.validateUserInformation
import com.example.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createUser(){
post("user/register"){
    val newUser = call.receive<User>()
    try {
        validateUserInformation(newUser)
        userDao.addUser(newUser)
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest,e.message.toString())
    }

    call.respond(HttpStatusCode.OK,"You have successfully registered!")
}
}
