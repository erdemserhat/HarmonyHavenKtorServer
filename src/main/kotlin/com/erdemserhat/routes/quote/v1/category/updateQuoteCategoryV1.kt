package com.erdemserhat.routes.quote.v1.category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateQuoteCategoryV1(){
    patch("/update-quote-category") {
        val category = call.receive<QuoteCategory>()
        DatabaseModule.quoteCategoryRepository.updateCategory(category)
        call.respond(HttpStatusCode.OK)
    }
}