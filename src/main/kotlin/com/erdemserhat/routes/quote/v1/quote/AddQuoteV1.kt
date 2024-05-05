package com.erdemserhat.routes.quote.v1.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addQuoteV1(){
    post("/add-quote") {
        val quote = call.receive<Quote>()
        DatabaseModule.quoteRepository.addQuote(quote)
        call.respond(HttpStatusCode.OK)
    }

}