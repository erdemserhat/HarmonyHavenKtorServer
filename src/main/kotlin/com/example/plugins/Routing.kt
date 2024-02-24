package com.example.plugins

import com.example.dao.userDao
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
                call.respondText("Hoş Geldin ${user.name}", status = HttpStatusCode.OK)
            }else{
                call.respondText("Yanlış Bilgiler", status = HttpStatusCode.NonAuthoritativeInformation)
            }



        }

        get("/user"){
            call.respondText("Serhat", status = HttpStatusCode.OK)
        }


    }
}
