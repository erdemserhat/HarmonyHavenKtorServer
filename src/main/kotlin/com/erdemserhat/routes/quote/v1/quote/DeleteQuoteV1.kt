package com.erdemserhat.routes.quote.v1.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteQuoteV1(){
    delete("/delete-quote/{quoteId}") {
        val quoteId = call.parameters["quoteId"]?.toIntOrNull()
        if (quoteId != null) {
            DatabaseModule.quoteRepository.removeQuote(quoteId)
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid quoteId")
        }
    }
}
