package com.erdemserhat.routes.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addQuoteV1() {
    post("/add-quote") {
        try {
            val quotes = call.receive<List<Quote>>()

            quotes.map {
                DatabaseModule.quoteRepository.addQuote(it)
            }

            call.respond(HttpStatusCode.OK)

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

}