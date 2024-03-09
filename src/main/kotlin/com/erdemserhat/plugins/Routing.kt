package com.erdemserhat.plugins

import com.erdemserhat.repository.MySQLUserRepository
import com.erdemserhat.routes.user.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repo = MySQLUserRepository()
    routing {
        get("/") {

            call.respond(repo.getAllUsers())

        }
        createUser()
        deleteUser()
        readUser()
        updateUser()
        resetPassword()


    }


}

