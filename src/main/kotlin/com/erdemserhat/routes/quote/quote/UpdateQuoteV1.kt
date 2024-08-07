package com.erdemserhat.routes.quote.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateQuoteV1(){
    patch("/update-quote") {
        val quote = call.receive<Quote>()
        DatabaseModule.quoteRepository.updateQuote(quote)
        call.respond(HttpStatusCode.OK)
    }

}