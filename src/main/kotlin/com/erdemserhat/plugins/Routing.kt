package com.erdemserhat.plugins

import com.erdemserhat.repository.user.UserRepository
import com.erdemserhat.routes.article.getAllArticles
import com.erdemserhat.routes.category.readCategories
import com.erdemserhat.routes.message.sendNotification
import com.erdemserhat.routes.user.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repo = UserRepository()
    routing {
        get("/") {

           call.respond("Harmony Haven Server")

        }

        //User Routes
        createUser()
        deleteUser()
        readUser()
        updateUser()
        resetPassword()

        //Article
        readCategories()
        getAllArticles()

        //Firebase Notification Service
        sendNotification()


    }


}

