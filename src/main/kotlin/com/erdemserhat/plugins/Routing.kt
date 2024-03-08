package com.erdemserhat.plugins

import com.erdemserhat.domain.email.sendEmail
import com.erdemserhat.repository.MySQLUserRepository
import com.erdemserhat.routes.user.createUser
import com.erdemserhat.routes.user.deleteUser
import com.erdemserhat.routes.user.readUser
import com.erdemserhat.routes.user.updateUser
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repo = MySQLUserRepository()
    routing {
        get("/") {

            call.respond(repo.getAllUsers())
            //repo.addUser(User(1, "yavuz", "1", "1", "1", "1", ""))
        }

        get("/user") {

            try{
                sendEmail(
                    to ="yssk32000@gmail.com",
                    subject= "example",
                    messageText = "forgotPasswordTemplate")

                call.respondText("Success")
            }catch (e:Exception){
                call.respondText(e.printStackTrace().toString())
            }


        }

        post("/example"){

        }

        createUser()
        deleteUser()
        readUser()
        updateUser()
    }
}

