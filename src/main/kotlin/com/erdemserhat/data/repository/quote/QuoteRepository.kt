package com.erdemserhat.data.repository.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.data.database.quote.DBQuoteEntity
import com.erdemserhat.data.database.quote.QuoteDao
import com.erdemserhat.data.database.quote.QuoteDaoImpl

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
        return quoteDao.getCategories().map { it.toQuote() }

    }
}

fun DBQuoteEntity.toQuote(): Quote {
    return Quote(
        id, quote, writer,imageUrl
    )

}