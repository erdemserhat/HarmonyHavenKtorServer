package com.erdemserhat.routes.notification

import com.erdemserhat.data.database.nosql.notification_preferences.*
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId


fun Route.notificationScheduler() {
    val notificationSchedulerRepository = DatabaseModule.notificationPreferencesRepository
    authenticate {
            post("/schedule-notification") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id

                val request = call.receive<NotificationSchedulerDto>()

                notificationSchedulerRepository.addNotificationPreferences(
                    request.toCollection(userId)
                )

                call.respond(status = HttpStatusCode.Created, message = "notification created successfully")
            } catch (ex: Exception) {
                ex.printStackTrace()
                call.respond(status = HttpStatusCode.InternalServerError, message = "an error occurred")

            }


        }

        delete("/schedule-notification/{id}") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id
                val notificationId = call.parameters["id"].toString()

                val isDeleted = notificationSchedulerRepository.deleteAllNotificationPreferences(
                    objectId = ObjectId(notificationId),
                    userId = userId
                )>0

                if (isDeleted) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    println("no content")

                    call.respond(HttpStatusCode.NoContent)
                }
            } catch (ex: Exception) {
                println(ex)

                call.respond(status = HttpStatusCode.InternalServerError, message = "an error occurred")

            }


        }

        patch("/schedule-notification") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id
                val request = call.receive<NotificationSchedulerDto>()
                val collection = request.toCollection(userId)

                val isUpdated = notificationSchedulerRepository.updateNotificationPreferences(
                    collection
                )

                if (isUpdated) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    println("no content")
                    call.respond(HttpStatusCode.NoContent)
                }
            } catch (ex: Exception) {
                println(ex)

                call.respond(status = HttpStatusCode.InternalServerError, message = "an error occurred")

            }


        }

        get("notification/schedulers") {
            try {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id
                val schedulers =
                    notificationSchedulerRepository.getNotificationPreferencesByUserId(userId).map {
                        it.toDto()
                    }

                call.respond(status = HttpStatusCode.OK, message = schedulers)
            } catch (ex: Exception) {
                ex.printStackTrace()
                call.respond(status = HttpStatusCode.InternalServerError, message = "an error occurred")

            }


        }

        get("notification/predefined-reminders") {
            try {
                call.respond(status = HttpStatusCode.OK, message = PredefinedReminderSubject.entries.toTypedArray())
            } catch (ex: Exception) {
                call.respond(status = HttpStatusCode.InternalServerError, message = "an error occurred")

            }


        }

        get("notification/predefined-messages") {
            try {
                call.respond(status = HttpStatusCode.OK, message = PredefinedMessageSubject.entries.toTypedArray())
            } catch (ex: Exception) {
                call.respond(status = HttpStatusCode.InternalServerError, message = "an error occurred")

            }


        }


    }

}
