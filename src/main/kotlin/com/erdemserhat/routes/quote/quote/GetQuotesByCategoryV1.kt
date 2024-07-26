package com.erdemserhat.routes.quote.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getQuotesByCategoryV1() {
    get("/get-quote-by-category/{categoryId}") {
        val categoryId = call.parameters["categoryId"]?.toIntOrNull()
        if (categoryId != null) {
            val quotes = DatabaseModule.quoteRepository.getQuotesByCategory(categoryId)
            call.respond(HttpStatusCode.OK, quotes)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid categoryId")
        }
    }
}
