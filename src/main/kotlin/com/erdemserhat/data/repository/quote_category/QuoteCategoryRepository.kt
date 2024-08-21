package com.erdemserhat.data.repository.quote_category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.data.database.quote_category.DBQuoteCategoryEntity
import com.erdemserhat.data.database.quote_category.QuoteCategoryDao
import com.erdemserhat.data.database.quote_category.QuoteCategoryDaoImpl

class QuoteCategoryRepository :QuoteCategoryContract{
    private val quoteCategoryDao:QuoteCategoryDao = QuoteCategoryDaoImpl()
    override fun addCategory(category: QuoteCategory): Boolean {
        return quoteCategoryDao.addCategory(category)>0
    }

    override fun removeCategory(quoteCategoryId: Int): Boolean {
        return quoteCategoryDao.removeCategory(quoteCategoryId)>0
    }

    override fun updateCategory(category: QuoteCategory): Boolean {
        return quoteCategoryDao.updateCategory(category)
    }

    override fun getCategories(): List<QuoteCategory> {
        return quoteCategoryDao.getCategories().map { it.toQuoteCategory() }
    }

}

fun DBQuoteCategoryEntity.toQuoteCategory():QuoteCategory{
    return QuoteCategory(
        id, name
    )
}