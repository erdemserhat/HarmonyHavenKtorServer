package com.erdemserhat.data.repository.quote_category

import com.erdemserhat.models.QuoteCategory

interface QuoteCategoryContract {
    fun addCategory(category: QuoteCategory): Boolean
    fun removeCategory(quoteCategoryId: Int): Boolean
    fun updateCategory(category: QuoteCategory):Boolean
    fun getCategories():List<QuoteCategory>
}