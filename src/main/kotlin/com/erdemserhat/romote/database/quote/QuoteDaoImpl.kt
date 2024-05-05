package com.erdemserhat.romote.database.quote

import com.erdemserhat.models.Quote
import com.erdemserhat.romote.database.DatabaseConfig
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import java.lang.reflect.Array.set

class QuoteDaoImpl() : QuoteDao {
    override fun addQuote(quote: Quote): Int {
        return DatabaseConfig.ktormDatabase.insert(DBQuoteTable) {
            set(DBQuoteTable.quote, quote.quote)
            set(DBQuoteTable.writer, quote.writer)
            set(DBQuoteTable.category_id, quote.categoryId)
        }
    }

    override fun removeQuote(quoteId: Int): Int {
        // Delete a category from the database
        return DatabaseConfig.ktormDatabase.delete(DBQuoteTable) {
            DBQuoteTable.id eq quoteId
        }

    }

    override fun updateQuote(quote: Quote): Boolean {
        try {
            DatabaseConfig.ktormDatabase.update(DBQuoteTable) {
                set(DBQuoteTable.quote, quote.quote)
                set(DBQuoteTable.writer, quote.writer)
                set(DBQuoteTable.category_id, quote.categoryId)
                where {
                    DBQuoteTable.id eq quote.id
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }

    }

    override fun getQuotesByCategory(quoteCategoryId: Int): List<DBQuoteEntity> {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBQuoteTable).filter {
            it.category_id eq quoteCategoryId
        }.toList()
    }
}