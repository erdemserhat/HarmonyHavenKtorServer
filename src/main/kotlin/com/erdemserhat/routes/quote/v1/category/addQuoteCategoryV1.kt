package com.erdemserhat.routes.quote.v1.category

import com.erdemserhat.models.Quote
import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequest(val name: String)

fun Route.addQuoteCategoryV1(){
    post("/add-quote-category") {
        val categoryRequest = call.receive<CategoryRequest>()
        val categoryName = categoryRequest.name
        DatabaseModule.quoteCategoryRepository.addCategory(QuoteCategory(name = categoryName))
        call.respond(HttpStatusCode.OK)
    }
}