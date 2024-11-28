package com.erdemserhat.data.database.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.data.database.DatabaseConfig
import com.erdemserhat.data.database.quote_category.DBQuoteCategoryTable
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class QuoteDaoImpl : QuoteDao {
    override suspend fun addQuote(quote: Quote): Int {
        return DatabaseConfig.ktormDatabase.insert(DBQuoteTable) {
            set(DBQuoteTable.quote, quote.quote)
            set(DBQuoteTable.writer, quote.writer)
            set(DBQuoteTable.imageUrl, quote.imageUrl)
            set(DBQuoteTable.quoteCategoryId, quote.quoteCategory)

        }
    }

    override suspend fun deleteQuoteById(id: Int): Int {
        return DatabaseConfig.ktormDatabase.delete(DBQuoteTable) {
            DBQuoteTable.id eq id

        }
    }

    override suspend fun deleteAll(): Int {
        // Delete a category from the database
        return DatabaseConfig.ktormDatabase.deleteAll(DBQuoteTable)

    }

    override suspend fun updateQuote(quote: Quote): Boolean {
        try {
            DatabaseConfig.ktormDatabase.update(DBQuoteTable) {
                set(DBQuoteTable.quote, quote.quote)
                set(DBQuoteTable.writer, quote.writer)
                set(DBQuoteTable.imageUrl, quote.imageUrl)
                set(DBQuoteTable.quoteCategoryId, quote.quoteCategory)
                where {
                    DBQuoteTable.id eq quote.id
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }

    }

    override suspend fun getCategories(): List<DBQuoteEntity> {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBQuoteTable).toList()
    }
}