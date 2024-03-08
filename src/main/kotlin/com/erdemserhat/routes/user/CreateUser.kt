package com.erdemserhat.routes.user

import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.domain.validateUserInformation
import com.erdemserhat.models.User
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
        userRepository.addUser(newUser)
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest,e.message.toString())
    }

    call.respond(HttpStatusCode.OK,"You have successfully registered!")
}
}
