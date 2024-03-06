package com.example.routes.user

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createUser(){
    get("/user/create"){
        call.respondText("Hello World.")
    }
}
