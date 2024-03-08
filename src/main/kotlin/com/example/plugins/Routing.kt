package com.example.plugins

import com.example.models.Message
import com.example.models.User
import com.example.models.UserLogin
import com.example.repository.MySQLUserRepository
import com.example.routes.user.createUser
import com.example.routes.user.deleteUser
import com.example.routes.user.readUser
import com.example.routes.user.updateUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repo = MySQLUserRepository()
    routing {
        get("/") {

            call.respond(repo.getAllUsers())
            //repo.addUser(User(1, "yavuz", "1", "1", "1", "1", ""))
        }

        post("/user") {



        }

        post("/example"){

        }

        createUser()
        deleteUser()
        readUser()
        updateUser()
    }
}

