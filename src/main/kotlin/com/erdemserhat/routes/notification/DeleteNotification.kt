package com.erdemserhat.routes.notification

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteNotification(){
    authenticate {
        delete("/delete-notification/{notificationId}"){
            val notificationId = call.parameters["notificationId"]?.toIntOrNull()
            if (notificationId != null) {
                DatabaseModule.notificationRepository.deleteNotification(notificationId)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid categoryId")
            }

        }
    }
}