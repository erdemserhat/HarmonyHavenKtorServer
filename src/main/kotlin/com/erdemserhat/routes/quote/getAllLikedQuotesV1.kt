package com.erdemserhat.routes.quote

import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllLikedQuotesV1() {
    authenticate {
        get("/get-liked-quotes") {
            val principal = call.principal<JWTPrincipal>()!!
            val userId = principal.payload.getClaim("id").asInt()
            val likedQuoteIdList = DatabaseModule.likedQuoteRepository.getAllLikedQuotesOfUser(userId)
            val likedQuotes = DatabaseModule.quoteRepository.getQuotes().filter {
                likedQuoteIdList.contains(it.id)
            } .map { it.convertToQuoteResponse(true) }

            call.respond(HttpStatusCode.OK, likedQuotes)

        }
    }

}