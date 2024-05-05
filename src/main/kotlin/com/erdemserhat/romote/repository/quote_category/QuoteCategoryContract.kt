package com.erdemserhat.romote.repository.quote_category

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.romote.database.quote_category.DBQuoteCategoryEntity

interface QuoteCategoryContract {
    fun addCategory(category: QuoteCategory): Boolean
    fun removeCategory(quoteCategoryId: Int): Boolean
    fun updateCategory(category: QuoteCategory):Boolean
    fun getCategories():List<QuoteCategory>
}