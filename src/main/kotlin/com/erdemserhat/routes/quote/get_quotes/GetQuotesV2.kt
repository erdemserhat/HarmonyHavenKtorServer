package com.erdemserhat.routes.quote.get_quotes

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getQuotesV2() {
    authenticate {
        get("/get-quotes") {
            val principal = call.principal<JWTPrincipal>()!!
            val userId = principal.payload.getClaim("id").asInt()
            val likedQuotes = DatabaseModule.likedQuoteRepository.getAllLikedQuotesOfUser(userId)
            val quoteList= DatabaseModule.quoteRepository.getQuotes().map {
                it.convertToQuoteResponse(likedQuotes.contains(it.id))
            }
            call.respond(HttpStatusCode.OK, quoteList)

        }
    }
}