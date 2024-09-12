package com.erdemserhat.data.repository.quote

import com.erdemserhat.models.Quote

interface QuoteRepositoryContract {
    suspend fun addQuote(quote: Quote): Boolean
    suspend fun deleteQuoteById(id: Int): Boolean
    suspend fun deleteAll()
    suspend fun updateQuote(quote: Quote):Boolean
    suspend fun getQuotes(): List<Quote>
}