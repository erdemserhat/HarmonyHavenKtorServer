package com.erdemserhat.data.repository.quote

import com.erdemserhat.models.Quote

interface QuoteRepositoryContract {
    fun addQuote(quote: Quote): Boolean
    fun deleteAll()
    fun updateQuote(quote: Quote):Boolean
    fun getQuotes(): List<Quote>
}