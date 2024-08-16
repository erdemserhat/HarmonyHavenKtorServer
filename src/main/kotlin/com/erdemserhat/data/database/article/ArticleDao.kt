package com.erdemserhat.data.database.article

import com.erdemserhat.models.Article

/**
 * Interface that defines operations for accessing article data in the database.
 */
interface ArticleDao {
    /**
     * Adds a new article to the database.
     *
     * @param article The article to add.
     * @return The ID of the newly added article.
     */
    fun addArticle(article: Article): Int

    /**
     * Updates an existing article in the database.
     *
     * @param articleId The ID of the article to update.
     * @param updatedArticle The updated article data.
     * @return `true` if the update was successful, `false` otherwise.
     */
    fun updateArticle(articleId: Int, updatedArticle: Article): Boolean

    /**
     * Deletes an article from the database.
     *
     * @param articleId The ID of the article to delete.
     * @return `true` if the deletion was successful, `false` otherwise.
     */
    fun deleteArticle(articleId: Int): Boolean

    /**
     * Retrieves an article from the database by its ID.
     *
     * @param articleId The ID of the article to retrieve.
     * @return The article entity if found, or `null` if not found.
     */
    suspend fun getArticle(articleId: Int): DBArticleEntity?

    /**
     * Retrieves all articles from the database.
     *
     * @return A list of all articles in the database.
     */
    fun getAllArticles(): List<DBArticleEntity>

    /**
     * Retrieves articles belonging to a specific category from the database.
     *
     * @param categoryId The ID of the category.
     * @return A list of articles belonging to the specified category.
     */
    fun getArticlesByCategory(categoryId: Int): List<DBArticleEntity>


}
