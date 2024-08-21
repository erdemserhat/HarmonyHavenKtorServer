package com.erdemserhat.data.repository.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.data.database.quote.DBQuoteEntity
import com.erdemserhat.data.database.quote.QuoteDao
import com.erdemserhat.data.database.quote.QuoteDaoImpl

class QuoteRepository : QuoteRepositoryContract {
    private val quoteDao: QuoteDao = QuoteDaoImpl()

    override fun addQuote(quote: Quote): Boolean {
        return quoteDao.addQuote(quote) > 0
    }

    override fun deleteAll() {
         quoteDao.deleteAll()
    }

    override fun updateQuote(quote: Quote): Boolean {
        return quoteDao.updateQuote(quote)
    }

    override fun getQuotes(): List<Quote> {
        return quoteDao.getCategories().map { it.toQuote() }

    }
}

fun DBQuoteEntity.toQuote(): Quote {
    return Quote(
        id, quote, writer,imageUrl
    )

}