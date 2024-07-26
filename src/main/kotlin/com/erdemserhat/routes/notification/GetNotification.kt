package com.erdemserhat.routes.notification

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getNotificationV1(){
    authenticate {
        get("/get-notifications") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()!!
            val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id

            // Sayfa numarası ve boyutunu URL parametrelerinden al
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 20

            val notifications = DatabaseModule.notificationRepository.getNotifications(userId,page,size)
            call.respond(
                message = notifications,
                status = HttpStatusCode.OK
            )

        }
    }

}