package com.erdemserhat.data.database.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.data.database.DatabaseConfig
import org.ktorm.dsl.*
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class QuoteDaoImpl() : QuoteDao {
    override fun addQuote(quote: Quote): Int {
        return DatabaseConfig.ktormDatabase.insert(DBQuoteTable) {
            set(DBQuoteTable.quote, quote.quote)
            set(DBQuoteTable.writer, quote.writer)
            set(DBQuoteTable.imageUrl, quote.imageUrl)

        }
    }

    override fun deleteAll(): Int {
        // Delete a category from the database
        return DatabaseConfig.ktormDatabase.deleteAll(DBQuoteTable)

    }

    override fun updateQuote(quote: Quote): Boolean {
        try {
            DatabaseConfig.ktormDatabase.update(DBQuoteTable) {
                set(DBQuoteTable.quote, quote.quote)
                set(DBQuoteTable.writer, quote.writer)
                set(DBQuoteTable.imageUrl, quote.imageUrl)
                where {
                    DBQuoteTable.id eq quote.id
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }

    }

    override fun getCategories(): List<DBQuoteEntity> {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBQuoteTable).toList()
    }
}