package com.erdemserhat.database.articleDao

import com.erdemserhat.database.categoryDao.DBCategoryEntity
import com.erdemserhat.models.Article
import com.erdemserhat.models.Category

interface ArticleDao {
    fun addArticle(article: Article):Int
    fun updateArticle(articleId:Int, updatedArticle: Article):Boolean
    fun deleteArticle(articleId:Int):Boolean
    suspend fun getArticle(articleId:Int): DBArticleEntity?
    fun getAllArticles():List<DBArticleEntity>
}