package com.erdemserhat.data.repository.quote_category

import com.erdemserhat.models.QuoteCategory

interface QuoteCategoryContract {
    suspend fun addCategory(category: QuoteCategory): Boolean
    suspend fun removeCategory(quoteCategoryId: Int): Boolean
    suspend fun updateCategory(category: QuoteCategory):Boolean
    suspend fun getCategories():List<QuoteCategory>
}