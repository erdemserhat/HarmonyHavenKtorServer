package com.erdemserhat.data.repository.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.models.QuoteResponse

interface QuoteRepositoryContract {
    suspend fun addQuote(quote: Quote): Boolean
    suspend fun deleteQuoteById(id: Int): Boolean
    suspend fun deleteAll()
    suspend fun updateQuote(quote: Quote):Boolean
    suspend fun getQuotes(): List<Quote>
    suspend fun getCategoriesWithPagination(page:Int, pageSize:Int,categoryIds:List<Int> = listOf(),seed:Int,userId:Int):List<QuoteResponse>
}