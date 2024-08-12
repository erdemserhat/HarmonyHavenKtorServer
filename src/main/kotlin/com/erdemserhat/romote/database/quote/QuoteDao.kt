package com.erdemserhat.romote.database.quote

import com.erdemserhat.models.Quote

interface QuoteDao {
    fun addQuote(quote: Quote): Int
    fun deleteAll(): Int
    fun updateQuote(quote: Quote):Boolean
    fun getCategories(): List<DBQuoteEntity>


}