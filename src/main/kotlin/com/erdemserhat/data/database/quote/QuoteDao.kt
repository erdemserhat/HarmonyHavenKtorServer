package com.erdemserhat.data.database.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.models.QuoteResponse

interface QuoteDao {
    suspend fun addQuote(quote: Quote): Int
    suspend fun deleteQuoteById(id: Int): Int
    suspend fun deleteAll(): Int
    suspend fun updateQuote(quote: Quote):Boolean
    suspend fun getQuotes(): List<DBQuoteEntity>
    suspend fun getCategoriesWithPagination(page:Int, pageSize:Int,categoryIds:List<Int> = listOf(), seed: Int,userId:Int):List<QuoteResponse>


}