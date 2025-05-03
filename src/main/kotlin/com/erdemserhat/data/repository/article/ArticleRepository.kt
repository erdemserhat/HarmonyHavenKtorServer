package com.erdemserhat.data.repository.article

import com.erdemserhat.data.database.sql.article.ArticleDao
import com.erdemserhat.data.database.sql.article.ArticleDaoImpl
import com.erdemserhat.data.database.sql.article.DBArticleEntity
import com.erdemserhat.data.database.sql.article_category.ArticleCategoryDao
import com.erdemserhat.data.database.sql.article_category.ArticleCategoryDaoImpl
import com.erdemserhat.service.di.DatabaseModule.articleCategoryRepository
import com.erdemserhat.models.ArticleDto
import com.erdemserhat.dto.requests.ArticleResponseType
import com.erdemserhat.models.toSlug

/**
 * Repository class for handling article-related operations.
 */
class ArticleRepository : ArticleRepositoryContract {
    private val articleDao: ArticleDao = ArticleDaoImpl()
    private val categoryDao: ArticleCategoryDao = ArticleCategoryDaoImpl()

    /**
     * Converts a DBArticleEntity to an Article model.
     */
    private fun DBArticleEntity.toArticle(): ArticleDto {
        return ArticleDto(
            id = id,
            title = title,
            slug = title.toSlug(),
            content = content,
            contentPreview = contentPreview,
            publishDate = publishDate,
            categoryId = categoryId,
            imagePath = imagePath
        )
    }

    /**
     * Adds an article to the database.
     */
    override suspend fun addArticle(articleDto: ArticleDto): Boolean {
        return articleDao.addArticle(articleDto) > 0
    }

    /**
     * Updates an existing article in the database.
     */
    override suspend fun updateArticle(articleId: Int, updatedArticleDto: ArticleDto): Boolean {
        return articleDao.updateArticle(articleId, updatedArticleDto)
    }

    /**
     * Deletes an article from the database.
     */
    override suspend fun deleteCategory(articleId: Int): Boolean {
        return articleDao.deleteArticle(articleId)
    }

    /**
     * Retrieves an article by its ID.
     */
    override suspend fun getArticle(articleId: Int): ArticleDto? {
        return articleDao.getArticle(articleId)?.toArticle()
    }

    /**
     * Retrieves all articles from the database.
     */
    override suspend fun getAllArticles(): List<ArticleDto> {
        return articleDao.getAllArticles().map {
            it.toArticle()

        }
    }

    /**
     * Retrieves articles belonging to a specific category.
     */
    override suspend fun getArticlesByCategory(categoryId: Int): List<ArticleResponseType> {
        return articleDao.getArticlesByCategory(categoryId).map { it.toArticleResponseType() }
    }

    /**
     * Converts a DBArticleEntity to an ArticleResponseType model.
     */
    private suspend fun DBArticleEntity.toArticleResponseType(): ArticleResponseType {
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
