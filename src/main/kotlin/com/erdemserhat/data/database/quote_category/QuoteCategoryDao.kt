package com.erdemserhat.data.database.quote_category

import com.erdemserhat.models.QuoteCategory

interface QuoteCategoryDao {
    fun addCategory(category: QuoteCategory): Int
    fun removeCategory(quoteCategoryId: Int): Int
    fun updateCategory(category: QuoteCategory):Boolean
    fun getCategories():List<DBQuoteCategoryEntity>
}