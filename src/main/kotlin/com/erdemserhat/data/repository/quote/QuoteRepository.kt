package com.erdemserhat.data.repository.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.data.database.sql.quote.DBQuoteEntity
import com.erdemserhat.data.database.sql.quote.QuoteDao
import com.erdemserhat.data.database.sql.quote.QuoteDaoImpl
import com.erdemserhat.models.QuoteResponse

class QuoteRepository : QuoteRepositoryContract {
    private val quoteDao: QuoteDao = QuoteDaoImpl()

    override suspend fun addQuote(quote: Quote): Boolean {
        return quoteDao.addQuote(quote) > 0
    }

    override suspend fun deleteQuoteById(id: Int): Boolean {
        return  quoteDao.deleteQuoteById(id) >0
    }

    override suspend fun deleteAll() {
         quoteDao.deleteAll()
    }

    override suspend fun updateQuote(quote: Quote): Boolean {
        return quoteDao.updateQuote(quote)
    }

    override suspend fun getQuotes(): List<Quote> {
        return quoteDao.getQuotes().map { it.toQuote() }

    }

    override suspend fun getCategoriesWithPagination(page: Int, pageSize: Int, categoryIds: List<Int>,seed:Int,userId:Int): List<QuoteResponse> {
        return quoteDao.getCategoriesWithPagination(page, pageSize, categoryIds,seed,userId)
    }
}

fun DBQuoteEntity.toQuote(): Quote {
    return Quote(
        id, quote, writer,imageUrl,quoteCategory
    )

}