package com.erdemserhat.data.database.sql.article_category

import com.erdemserhat.models.ArticleCategory



interface ArticleCategoryDao {
    suspend fun addCategory(category: ArticleCategory): Int
    suspend fun updateCategory(categoryId: Int, updatedCategory: ArticleCategory): Boolean
    suspend fun deleteCategory(categoryId: Int): Boolean
    suspend fun getCategory(categoryId: Int): DBArticleCategoryEntity?
    suspend fun getAllCategory(): List<DBArticleCategoryEntity>
}
