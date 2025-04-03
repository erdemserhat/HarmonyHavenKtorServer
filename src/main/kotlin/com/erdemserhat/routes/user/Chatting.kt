

package com.erdemserhat.routes.user

import com.erdemserhat.OpenAIClient
import com.erdemserhat.OpenAiChatService
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.di.DatabaseModule.userRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap


fun Route.chatting() {
    authenticate {
        sse("/chat/{prompt}") {  // Path parametresi ile prompt alıyoruz
            val prompt = call.parameters["prompt"] ?: return@sse call.respond(HttpStatusCode.BadRequest, "Prompt is required")

            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val user = userRepository.getUserByEmailMinimizedVersion(email!!)

            OpenAiChatService.sendMessage(
                prompt = prompt,
                username = user!!.name,
                userId = user.id
            ).collect {
                send(it)
            }

            close()

            call.respond(HttpStatusCode.OK)
        }
    }
}
