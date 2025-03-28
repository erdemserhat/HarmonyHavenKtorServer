package com.erdemserhat.routes.user

import com.erdemserhat.models.toDto
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getNotifications() {
    authenticate {
        get("/user/get-notifications") {
            // Kullanıcı kimlik doğrulamasını alın
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            if (email == null) {
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@get
            }

            // Kullanıcı ID'sini veritabanından alın
            val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)?.id
            if (userId == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
                return@get
            }

            // Sayfa numarası ve boyutunu alın
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val size = call.request.queryParameters["size"]?.toIntOrNull() ?: 20

            // Bildirimleri alın ve DTO'ya dönüştürün
            val userNotifications = DatabaseModule.notificationRepository
                .getNotifications(userId, page, size)
                .map { it.toDto() }

            // Bildirimleri yanıt olarak gönderin
            call.respond(
                status = HttpStatusCode.OK,
                message = userNotifications
            )
        }
    }
}
