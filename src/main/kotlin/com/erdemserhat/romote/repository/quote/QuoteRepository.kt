package com.erdemserhat.romote.repository.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.romote.database.article_category.ArticleCategoryDao
import com.erdemserhat.romote.database.article_category.ArticleCategoryDaoImpl
import com.erdemserhat.romote.database.quote.DBQuoteEntity
import com.erdemserhat.romote.database.quote.DBQuoteTable
import com.erdemserhat.romote.database.quote.QuoteDao
import com.erdemserhat.romote.database.quote.QuoteDaoImpl

class QuoteRepository() : QuoteRepositoryContract {
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
        id, quote, writer
    )

}