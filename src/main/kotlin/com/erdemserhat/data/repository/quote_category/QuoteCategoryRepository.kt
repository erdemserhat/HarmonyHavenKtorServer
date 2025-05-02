package com.erdemserhat.data.repository.quote_category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.data.database.sql.quote_category.DBQuoteCategoryEntity
import com.erdemserhat.data.database.sql.quote_category.QuoteCategoryDao
import com.erdemserhat.data.database.sql.quote_category.QuoteCategoryDaoImpl

class QuoteCategoryRepository :QuoteCategoryContract{
    private val quoteCategoryDao:QuoteCategoryDao = QuoteCategoryDaoImpl()
    override suspend fun addCategory(category: QuoteCategory): Boolean {
        return quoteCategoryDao.addCategory(category)>0
    }

    override suspend fun removeCategory(quoteCategoryId: Int): Boolean {
        return quoteCategoryDao.removeCategory(quoteCategoryId)>0
    }

    override suspend fun updateCategory(category: QuoteCategory): Boolean {
        return quoteCategoryDao.updateCategory(category)
    }

    override suspend fun getCategories(): List<QuoteCategory> {
        return quoteCategoryDao.getCategories().map { it.toQuoteCategory() }
    }

}

fun DBQuoteCategoryEntity.toQuoteCategory():QuoteCategory{
    return QuoteCategory(
        id, name
    )
}