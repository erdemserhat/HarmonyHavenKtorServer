package com.erdemserhat.routes.user

import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.openai.ChatRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap

val chatRequest = ChatRequest()

// Kullanıcıların son mesaj gönderme zamanlarını tutacak map
private val userLastMessageTimes = ConcurrentHashMap<Int, LocalDateTime>()

// Kullanıcıların 1 dakikadaki mesaj sayılarını tutacak map
private val userMessageCounts = ConcurrentHashMap<Int, Int>()

// Mesaj sayılarını sıfırlamak için zamanlayıcı
private val messageCountResetTimes = ConcurrentHashMap<Int, LocalDateTime>()

fun Route.chatting() {
    authenticate {
        post("/chat") {
            try {
                val prompt = call.receive<ChatDto>()
                
                // Mesaj uzunluğu kontrolü
                if (prompt.text.length > 500) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message =  ChatDto("Mesajınız çok uzun. Lütfen 500 karakterden kısa bir mesaj gönderin.")


                    )
                    return@post
                }

                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()
                val user = DatabaseModule.userRepository.getUserByEmailInformation(email!!)
                val userId = user!!.id

                // Spam kontrolü
                val now = LocalDateTime.now()
                val lastMessageTime = userLastMessageTimes[userId]
                val messageCount = userMessageCounts[userId] ?: 0
                val resetTime = messageCountResetTimes[userId]

                // 1 dakika geçtiyse sayaçları sıfırla
                if (resetTime != null && ChronoUnit.MINUTES.between(resetTime, now) >= 1) {
                    userMessageCounts[userId] = 0
                    messageCountResetTimes[userId] = now
                }

                // Spam kontrolü
                if (lastMessageTime != null) {
                    val secondsSinceLastMessage = ChronoUnit.SECONDS.between(lastMessageTime, now)
                    if (secondsSinceLastMessage < 2) { // En az 2 saniye bekleme süresi
                        call.respond(
                            status = HttpStatusCode.TooManyRequests,
                            message =  ChatDto("Lütfen mesajlar arasında biraz bekleyin.")
                        )
                        return@post
                    }
                }

                // 1 dakikada maksimum 10 mesaj kontrolü
                if (messageCount >= 10) {
                    call.respond(
                        status = HttpStatusCode.TooManyRequests,
                        message = ChatDto("Çok fazla mesaj gönderdiniz. Lütfen bir dakika bekleyin.")
                    )
                    return@post
                }

                // Mesaj sayısını artır ve zamanları güncelle
                userMessageCounts[userId] = messageCount + 1
                userLastMessageTimes[userId] = now
                if (messageCountResetTimes[userId] == null) {
                    messageCountResetTimes[userId] = now
                }

                val response = chatRequest.sendMessage(
                    userId = userId,
                    userName = user.name,
                    prompt = prompt.text
                )

                call.respond(status = HttpStatusCode.OK, ChatDto(response))

            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(status = HttpStatusCode.InternalServerError, e.localizedMessage)
            }
        }
    }
}

@Serializable
data class ChatDto(val text: String)