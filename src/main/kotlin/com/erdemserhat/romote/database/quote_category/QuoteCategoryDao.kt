package com.erdemserhat.romote.database.quote_category

import com.erdemserhat.models.Quote
import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.romote.database.article_category.DBArticleCategoryEntity

interface QuoteCategoryDao {
    fun addCategory(category: QuoteCategory): Int
    fun removeCategory(quoteCategoryId: Int): Int
    fun updateCategory(category: QuoteCategory):Boolean
    fun getCategories():List<DBQuoteCategoryEntity>
}