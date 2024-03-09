package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validation.validateUserInformation
import com.erdemserhat.models.Message
import com.erdemserhat.models.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createUser(){
post("user/register"){
    val newUser = call.receive<User>()
    try {
        validateUserInformation(newUser)
        userRepository.addUser(newUser)
        call.respond(Message("You have successfully registered"))

    } catch (e: Exception) {
        call.respond(Message(e.message.toString()))
    }
    //sendWelcomeMail(newUser.email,newUser.name)
}
}
