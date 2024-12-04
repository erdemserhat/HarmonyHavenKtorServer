package com.erdemserhat.routes.quote.get_quotes

import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.getQuotesV3() {
    authenticate {
        post("/get-quotes") {
            val principal = call.principal<JWTPrincipal>()!!
            val userId = principal.payload.getClaim("id").asInt()
            val request = call.receive<FilteredQuoteRequest>()

            val result = DatabaseModule.quoteRepository.getCategoriesWithPagination(
                page = request.page,
                pageSize = request.pageSize,
                categoryIds = request.categories,
                seed = request.seed,
                userId = userId
            )

            call.respond(
                status = HttpStatusCode.OK,
                message = result
            )



        }

    }

}

@Serializable
data class FilteredQuoteRequest(
    val categories: List<Int>,
    val page: Int = 1,
    val pageSize: Int = 10,
    val seed:Int
)

@Serializable
data class PaginatedResponse(
    val quotes: List<Quote>,
    val currentPage: Int,
    val pageSize: Int,
    val totalCount: Int,
    val hasNextPage: Boolean
)