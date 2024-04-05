package com.erdemserhat.routes.admin

import com.erdemserhat.dto.requests.OpenAIPromptDto
import com.erdemserhat.service.openai.OpenAIRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun Route.openAIRequestV1() {
    authenticate {
        post("/openai-request") {
            // Get the role of the authenticated user
            val principal = call.principal<JWTPrincipal>()
            val role = principal?.payload?.getClaim("role")?.asString()

            // Check if the authenticated user has admin role
            if (role != "admin") {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = "You are not allowed to use this service"
                )
                return@post
            }


            val request = call.receive<OpenAIPromptDto>()
            val openaiRequest = OpenAIRequest(request.prompt)
            call.respond(openaiRequest.getResult())

        }
    }



}