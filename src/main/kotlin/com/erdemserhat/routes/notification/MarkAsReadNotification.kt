package com.erdemserhat.routes.notification

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.markAsReadNotification() {
    authenticate {
        patch("/mark-as-read-notification/{notificationId}") {
            val notificationId = call.parameters["notificationId"]?.toIntOrNull()
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()

            if (notificationId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid notificationId")
                return@patch
            }

            if (email == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@patch
            }

            val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)?.id
            if (userId == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
                return@patch
            }

            val isNotificationOwner = DatabaseModule.notificationRepository.getNotifications(userId)
                .any { it.id == notificationId }

            if (!isNotificationOwner) {
                call.respond(HttpStatusCode.Forbidden, "User is not the owner of the notification")
                return@patch
            }

            DatabaseModule.notificationRepository.markAsRead(notificationId)
            call.respond(HttpStatusCode.OK)
        }
    }
}
