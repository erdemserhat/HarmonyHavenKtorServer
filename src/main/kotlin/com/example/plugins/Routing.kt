package com.example.plugins

import com.example.dao.userDao
import com.example.models.Message
import com.example.models.UserLogin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/user") {
            val loginData = call.receive<UserLogin>()
            val user = (userDao.allUsers().find { it.password==loginData.password && it.email==loginData.email })
            if(user!=null){
                call.respond(Message("Welcome ${user.name}"))
            }else{
                call.respond(Message("There is no user like that"))
            }

        }

        get("/user/update") {
            userDao.editUser(8,"Ali","Erdem","ali@example.com","ali123")

        }



    }
}
