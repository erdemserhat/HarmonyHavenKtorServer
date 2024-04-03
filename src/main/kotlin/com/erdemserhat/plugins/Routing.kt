package com.erdemserhat.plugins

import com.erdemserhat.di.DatabaseModule
import com.erdemserhat.repository.user.UserRepository
import com.erdemserhat.routes.admin.deleteUserAdmin
import com.erdemserhat.routes.article.getAllArticles
import com.erdemserhat.routes.authenticate
import com.erdemserhat.routes.category.readCategories
import com.erdemserhat.routes.getSecretInfo
import com.erdemserhat.routes.message.sendNotification
import com.erdemserhat.routes.signIn
import com.erdemserhat.routes.signUp
import com.erdemserhat.routes.user.*
import com.erdemserhat.security.hashing.SHA256HashingService
import com.erdemserhat.security.token.TokenConfig
import com.erdemserhat.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.log

fun Application.configureRouting(
) {
    val repo = UserRepository()
    routing {
        get("/") {

           call.respond("Harmony Haven Server")

        }


        //User Routes
        registerUserV1()
        deleteUser()
        authenticateUserV1()
        updateUser()
        resetPassword()

        //Article
        readCategories()
        getAllArticles()

        //Firebase Notification Service
        sendNotification()

        //authenticate()
       // getSecretInfo()

        //admin
        deleteUserAdmin()




    }


}

