package com.erdemserhat.romote.database.quote_category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.romote.database.DatabaseConfig
import com.erdemserhat.romote.database.article_category.DBArticleCategoryEntity
import com.erdemserhat.romote.database.quote.DBQuoteTable
import com.erdemserhat.service.di.DatabaseModule
import org.jetbrains.exposed.sql.statements.DeleteStatement.Companion.where
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class QuoteCategoryDaoImpl() : QuoteCategoryDao {
    override fun addCategory(category: QuoteCategory): Int {
        return DatabaseConfig.ktormDatabase.insert(DBQuoteCategoryTable) {
            set(DBQuoteCategoryTable.name, category.name)
        }
    }

    override fun removeCategory(quoteCategoryId: Int): Int {
        return DatabaseConfig.ktormDatabase.delete(DBQuoteCategoryTable) {
            DBQuoteCategoryTable.id eq quoteCategoryId

        }
    }

    override fun updateCategory(category: QuoteCategory): Boolean {
        try {
            DatabaseConfig.ktormDatabase.update(DBQuoteCategoryTable) {
                set(DBQuoteCategoryTable.name, category.name)
                where {
                    DBQuoteCategoryTable.id eq category.id
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun getCategories(): List<DBQuoteCategoryEntity> {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBQuoteCategoryTable).toList()
    }

}
