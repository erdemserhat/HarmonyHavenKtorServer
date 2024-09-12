package com.erdemserhat.data.database.quote_category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.data.database.DatabaseConfig
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class QuoteCategoryDaoImpl : QuoteCategoryDao {
    override suspend fun addCategory(category: QuoteCategory): Int {
        return DatabaseConfig.ktormDatabase.insert(DBQuoteCategoryTable) {
            set(DBQuoteCategoryTable.name, category.name)
        }
    }

    override suspend fun removeCategory(quoteCategoryId: Int): Int {
        return DatabaseConfig.ktormDatabase.delete(DBQuoteCategoryTable) {
            DBQuoteCategoryTable.id eq quoteCategoryId

        }
    }

    override suspend fun updateCategory(category: QuoteCategory): Boolean {
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

    override suspend fun getCategories(): List<DBQuoteCategoryEntity> {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBQuoteCategoryTable).toList()
    }

}
