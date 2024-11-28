package com.erdemserhat.routes.quote

import com.erdemserhat.dto.requests.QuoteLikeDto
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.removeLikeQuoteV1() {
    authenticate {
        post("remove-like-quote/{quoteId}") {
            val principal = call.principal<JWTPrincipal>()
            val quoteId = call.parameters["quoteId"]?.toIntOrNull() // Path variable'ı al
            val userId = principal!!.payload.claims["id"]!!.asInt()
            DatabaseModule.likedQuoteRepository.removeLike(userId, quoteId!!)
            call.respond(HttpStatusCode.OK)

        }

    }
}