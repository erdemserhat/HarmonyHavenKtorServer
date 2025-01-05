package com.erdemserhat.data.database.article

import com.erdemserhat.models.Article

interface ArticleDao {
    suspend fun addArticle(article: Article): Int
    suspend fun updateArticle(articleId: Int, updatedArticle: Article): Boolean
    suspend fun deleteArticle(articleId: Int): Boolean
    suspend fun getArticle(articleId: Int): DBArticleEntity?
    suspend fun getAllArticles(): List<DBArticleEntity>
    suspend fun getArticlesByCategory(categoryId: Int): List<DBArticleEntity>


}
