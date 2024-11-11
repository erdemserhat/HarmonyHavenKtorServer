package com.erdemserhat.routes.quote

import com.erdemserhat.dto.requests.QuoteLikeDto
import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.likeQuote() {
    authenticate {
        post("/like-quote/{quoteId}") { // quoteId path parametresi
            val principal = call.principal<JWTPrincipal>()
            val quoteId = call.parameters["quoteId"]?.toIntOrNull() // Path variable'ı al
            val userId = principal!!.payload.claims["id"]!!.asInt()

            if (quoteId != null) {
                print("UserId $userId and quoteId = $quoteId")
                // `likeQuote` fonksiyonuna quoteId'yi gönder
                DatabaseModule.likedQuoteRepository.likeQuote(userId, quoteId)
                call.respond(HttpStatusCode.OK)
            } else {
                // Eğer quoteId geçerli değilse hata döndür
                call.respond(HttpStatusCode.BadRequest, "Invalid quoteId")
            }
        }
    }
}