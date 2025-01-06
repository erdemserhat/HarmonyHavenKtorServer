package com.erdemserhat.routes.comment

import com.erdemserhat.service.commentservice.CommentServiceContract
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.commentRoutes() {
    val commentService: CommentServiceContract = DatabaseModule.commentService
    authenticate {

        route("/comments") {
            post {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id

                val parameters = call.receive<Map<String, String>>()
                val postId = parameters["postId"]?.toIntOrNull()
                val comment = parameters["comment"]

                if (postId == null || comment.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid request parameters")
                    return@post
                }

                commentService.addComment(postId, userId, comment)
                call.respond(HttpStatusCode.Created, "Comment added successfully")
            }


            delete("/{commentId}") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id

                val commentId = call.parameters["commentId"]?.toIntOrNull()



                if (commentId == null || userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid request parameters")
                    return@delete
                }

                commentService.deleteComment(commentId, userId)
                call.respond(HttpStatusCode.OK, "Comment deleted successfully")
            }


            get("/post/{postId}") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id

                val postId = call.parameters["postId"]?.toIntOrNull()

                if (postId == null || userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid request parameters")
                    return@get
                }

                val comments = commentService.getCommentsByPostId(postId, userId)
                call.respond(HttpStatusCode.OK, comments)
            }

            post("/{commentId}/like") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id

                val commentId = call.parameters["commentId"]?.toIntOrNull()

                if (commentId == null || userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid request parameters")
                    return@post
                }

                commentService.likeComment(commentId, userId)
                call.respond(HttpStatusCode.OK, "Comment liked successfully")
            }

            post("/{commentId}/unlike") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()!!
                val userId = DatabaseModule.userRepository.getUserByEmailInformation(email)!!.id

                val commentId = call.parameters["commentId"]?.toIntOrNull()

                if (commentId == null || userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid request parameters")
                    return@post
                }

                commentService.unlikeComment(commentId, userId)
                call.respond(HttpStatusCode.OK, "Comment unliked successfully")
            }
        }
    }
}
