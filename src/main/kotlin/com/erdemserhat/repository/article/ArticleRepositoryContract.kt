package com.erdemserhat.repository.article

import com.erdemserhat.models.Article
import com.erdemserhat.models.Category
import com.erdemserhat.models.rest.client.ArticleResponseType

interface ArticleRepositoryContract {
    fun addArticle(article: Article):Boolean
    fun updateArticle(articleId:Int, updatedArticle: Article):Boolean
    fun deleteCategory(articleId:Int):Boolean
    suspend fun getArticle(articleId:Int): ArticleResponseType?
    fun getAllArticles():List<ArticleResponseType>
    fun getArticlesByCategory(categoryId:Int):List<ArticleResponseType>
}