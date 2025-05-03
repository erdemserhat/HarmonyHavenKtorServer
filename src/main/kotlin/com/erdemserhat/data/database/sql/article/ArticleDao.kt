package com.erdemserhat.data.database.sql.article

import com.erdemserhat.models.ArticleDto

interface ArticleDao {
    suspend fun addArticle(articleDto: ArticleDto): Int
    suspend fun updateArticle(articleId: Int, updatedArticleDto: ArticleDto): Boolean
    suspend fun deleteArticle(articleId: Int): Boolean
    suspend fun getArticle(articleId: Int): DBArticleEntity?
    suspend fun getAllArticles(): List<DBArticleEntity>
    suspend fun getArticlesByCategory(categoryId: Int): List<DBArticleEntity>


}
