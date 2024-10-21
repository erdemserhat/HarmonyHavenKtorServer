package com.erdemserhat.data.database.quote_category

import com.erdemserhat.models.QuoteCategory

interface QuoteCategoryDao {
    suspend fun addCategory(category: QuoteCategory): Int
    suspend fun removeCategory(quoteCategoryId: Int): Int
    suspend fun updateCategory(category: QuoteCategory):Boolean
    suspend fun getCategories():List<DBQuoteCategoryEntity>
}