package com.erdemserhat.data.repository.article

import com.erdemserhat.models.ArticleDto
import com.erdemserhat.dto.requests.ArticleResponseType

/**
 * Interface defining the contract for article repository operations.
 */
interface ArticleRepositoryContract {
    /**
     * Adds an article to the repository.
     * @param articleDto The article to add.
     * @return True if the article was added successfully, false otherwise.
     */
    suspend fun addArticle(articleDto: ArticleDto): Boolean

    /**
     * Updates an existing article in the repository.
     * @param articleId The ID of the article to update.
     * @param updatedArticleDto The updated article data.
     * @return True if the article was updated successfully, false otherwise.
     */
    suspend fun updateArticle(articleId: Int, updatedArticleDto: ArticleDto): Boolean

    /**
     * Deletes an article from the repository.
     * @param articleId The ID of the article to delete.
     * @return True if the article was deleted successfully, false otherwise.
     */
    suspend fun deleteCategory(articleId: Int): Boolean

    /**
     * Retrieves an article by its ID from the repository.
     * @param articleId The ID of the article to retrieve.
     * @return An instance of [ArticleResponseType] representing the retrieved article, or null if not found.
     */
    suspend  fun getArticle(articleId: Int): ArticleDto?

    /**
     * Retrieves all articles from the repository.
     * @return A list of [ArticleDto] objects representing all articles in the repository.
     */
    suspend fun getAllArticles(): List<ArticleDto>

    /**
     * Retrieves articles belonging to a specific category from the repository.
     * @param categoryId The ID of the category.
     * @return A list of [ArticleResponseType] objects representing articles belonging to the specified category.
     */
    suspend fun getArticlesByCategory(categoryId: Int): List<ArticleResponseType>
}
