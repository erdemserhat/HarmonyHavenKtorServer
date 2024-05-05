package com.erdemserhat.romote.database.quote

import com.erdemserhat.models.Quote

interface QuoteDao {
    fun addQuote(quote: Quote): Int
    fun removeQuote(quoteId: Int): Int
    fun updateQuote(quote: Quote):Boolean

    fun getQuotesByCategory(quoteCategoryId: Int): List<DBQuoteEntity>


}