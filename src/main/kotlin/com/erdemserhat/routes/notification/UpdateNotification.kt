package com.erdemserhat.routes.notification

import com.erdemserhat.models.Notification
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.util.Role
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateNotification() {
    authenticate {
        patch("/update-notification") {
            //Calling Jwt to Authenticate User
            val principal = call.principal<JWTPrincipal>()
            val updatedNotification = call.receive<Notification>()

            //Check the payload
            val email = principal?.payload?.getClaim("email")?.asString()
            val role = principal?.payload?.getClaim("role")?.asString()

            if (email==null || role ==null){
                call.respond(
                    message = "There was a problem with your jwt",
                    status = HttpStatusCode.Unauthorized
                )
                return@patch
            }
            //check role
            if(role!= Role.ADMIN){
                call.respond(
                    message = "Your are not admin",
                    status = HttpStatusCode.Unauthorized
                )
                return@patch

            }

            //check notification existence
            val result = DatabaseModule.notificationRepository.updateNotification(updatedNotification)

            if(!result){
                call.respond(
                    message = "There is no notification to be updated",
                    status = HttpStatusCode.NotFound
                )
                return@patch
            }

            call.respond(
                message = "Operation OK",
                status = HttpStatusCode.Unauthorized
            )
            return@patch







        }
    }
}