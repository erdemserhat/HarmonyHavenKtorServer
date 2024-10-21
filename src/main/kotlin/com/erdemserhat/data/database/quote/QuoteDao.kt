package com.erdemserhat.data.database.quote

import com.erdemserhat.models.Quote

interface QuoteDao {
    suspend fun addQuote(quote: Quote): Int
    suspend fun deleteQuoteById(id: Int): Int
    suspend fun deleteAll(): Int
    suspend fun updateQuote(quote: Quote):Boolean
    suspend fun getCategories(): List<DBQuoteEntity>


}