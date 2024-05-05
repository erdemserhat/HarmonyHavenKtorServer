package com.erdemserhat.romote.repository.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.romote.database.quote.DBQuoteEntity

interface QuoteRepositoryContract {
    fun addQuote(quote: Quote): Boolean
    fun removeQuote(quoteId: Int): Boolean
    fun updateQuote(quote: Quote):Boolean

    fun getQuotesByCategory(quoteCategoryId: Int): List<Quote>
}