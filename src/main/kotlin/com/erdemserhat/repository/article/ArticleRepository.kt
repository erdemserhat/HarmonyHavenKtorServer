package com.erdemserhat.repository.article

import com.erdemserhat.database.articleDao.ArticleDao
import com.erdemserhat.database.articleDao.ArticleDaoImpl
import com.erdemserhat.database.articleDao.DBArticleEntity
import com.erdemserhat.database.categoryDao.CategoryDao
import com.erdemserhat.database.categoryDao.CategoryDaoImpl
import com.erdemserhat.database.categoryDao.DBCategoryEntity
import com.erdemserhat.di.DatabaseModule.categoryRepository
import com.erdemserhat.models.Article
import com.erdemserhat.models.Category
import com.erdemserhat.models.rest.client.ArticleResponseType
import com.erdemserhat.repository.category.toCategory

class ArticleRepository : ArticleRepositoryContract {
    private val articleDao : ArticleDao = ArticleDaoImpl()
    private val categoryDao : CategoryDao = CategoryDaoImpl()

    private fun DBArticleEntity.toArticle():Article{
        return Article(
            id = id,
            title = title,
            content = content,
            publishDate = publishDate,
            categoryId = categoryId,
            imagePath = imagePath

        )
    }


    override fun addArticle(article: Article): Boolean {
        return articleDao.addArticle(article) >0
    }

    override fun updateArticle(articleId: Int, updatedArticle: Article): Boolean {
        return articleDao.updateArticle(articleId,updatedArticle)
    }

    override fun deleteCategory(articleId: Int): Boolean {
        return articleDao.deleteArticle(articleId)
    }

    override suspend fun getArticle(articleId: Int): ArticleResponseType? {
        return articleDao.getArticle(articleId)?.toArticleResponseType()
    }

    override fun getAllArticles(): List<ArticleResponseType> {
        return articleDao.getAllArticles().map { it.toArticleResponseType() }
    }

    override fun getArticlesByCategory(categoryId: Int): List<ArticleResponseType> {
        return articleDao.getArticlesByCategory(categoryId).map { it.toArticleResponseType() }.toList()
    }

}

 fun DBArticleEntity.toArticleResponseType():ArticleResponseType{
    return ArticleResponseType(
        id = id,
        title = title,
        content = content,
        publishDate = publishDate,
        category =categoryRepository.getCategory(categoryId)?.name,
        imagePath = imagePath

    )
}
