package com.erdemserhat.data.database.sql.quote_category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.data.database.sql.MySqlDatabaseConfig
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class QuoteCategoryDaoImpl : QuoteCategoryDao {
    override suspend fun addCategory(category: QuoteCategory): Int {
        return MySqlDatabaseConfig.ktormDatabase.insert(DBQuoteCategoryTable) {
            set(DBQuoteCategoryTable.name, category.name)
        }
    }

    override suspend fun removeCategory(quoteCategoryId: Int): Int {
        return MySqlDatabaseConfig.ktormDatabase.delete(DBQuoteCategoryTable) {
            DBQuoteCategoryTable.id eq quoteCategoryId

        }
    }

    override suspend fun updateCategory(category: QuoteCategory): Boolean {
        try {
            MySqlDatabaseConfig.ktormDatabase.update(DBQuoteCategoryTable) {
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
        return MySqlDatabaseConfig.ktormDatabase.sequenceOf(DBQuoteCategoryTable).toList()
    }

}
