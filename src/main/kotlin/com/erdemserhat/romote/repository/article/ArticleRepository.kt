package com.erdemserhat.romote.repository.article

import com.erdemserhat.romote.database.article.ArticleDao
import com.erdemserhat.romote.database.article.ArticleDaoImpl
import com.erdemserhat.romote.database.article.DBArticleEntity
import com.erdemserhat.romote.database.article_category.ArticleCategoryDao
import com.erdemserhat.romote.database.article_category.ArticleCategoryDaoImpl
import com.erdemserhat.service.di.DatabaseModule.articleCategoryRepository
import com.erdemserhat.models.Article
import com.erdemserhat.dto.requests.ArticleResponseType

/**
 * Repository class for handling article-related operations.
 */
class ArticleRepository : ArticleRepositoryContract {
    private val articleDao: ArticleDao = ArticleDaoImpl()
    private val categoryDao: ArticleCategoryDao = ArticleCategoryDaoImpl()

    /**
     * Converts a DBArticleEntity to an Article model.
     */
    private fun DBArticleEntity.toArticle(): Article {
        return Article(
            id = id,
            title = title,
            content = content,
            publishDate = publishDate,
            categoryId = categoryId,
            imagePath = imagePath
        )
    }

    /**
     * Adds an article to the database.
     */
    override fun addArticle(article: Article): Boolean {
        return articleDao.addArticle(article) > 0
    }

    /**
     * Updates an existing article in the database.
     */
    override fun updateArticle(articleId: Int, updatedArticle: Article): Boolean {
        return articleDao.updateArticle(articleId, updatedArticle)
    }

    /**
     * Deletes an article from the database.
     */
    override fun deleteCategory(articleId: Int): Boolean {
        return articleDao.deleteArticle(articleId)
    }

    /**
     * Retrieves an article by its ID.
     */
    override suspend fun getArticle(articleId: Int): ArticleResponseType? {
        return articleDao.getArticle(articleId)?.toArticleResponseType()
    }

    /**
     * Retrieves all articles from the database.
     */
    override fun getAllArticles(): List<Article> {
        return articleDao.getAllArticles().map { it.toArticle() }
    }

    /**
     * Retrieves articles belonging to a specific category.
     */
    override fun getArticlesByCategory(categoryId: Int): List<ArticleResponseType> {
        return articleDao.getArticlesByCategory(categoryId).map { it.toArticleResponseType() }
    }

    /**
     * Converts a DBArticleEntity to an ArticleResponseType model.
     */
    private fun DBArticleEntity.toArticleResponseType(): ArticleResponseType {
        return ArticleResponseType(
            id = id,
            title = title,
            content = content,
            publishDate = publishDate,
            category = articleCategoryRepository.getCategory(categoryId)?.name,
            imagePath = imagePath
        )
    }
}
