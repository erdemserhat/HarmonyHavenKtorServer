package com.erdemserhat.romote.repository.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.romote.database.quote.DBQuoteEntity

interface QuoteRepositoryContract {
    fun addQuote(quote: Quote): Boolean
    fun deleteAll()
    fun updateQuote(quote: Quote):Boolean
    fun getQuotes(): List<Quote>
}