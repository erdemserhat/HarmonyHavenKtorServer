package com.erdemserhat.data.repository.article_category

import com.erdemserhat.models.ArticleCategory

/**
 * Interface defining operations for managing category data.
 */
interface ArticleCategoryRepositoryContract {

    /**
     * Adds a new category to the repository.
     *
     * @param category The category to be added.
     * @return `true` if the operation is successful, `false` otherwise.
     */
    fun addCategory(category: ArticleCategory): Boolean

    /**
     * Updates an existing category in the repository.
     *
     * @param categoryId The ID of the category to be updated.
     * @param updatedCategory The updated category information.
     * @return `true` if the update is successful, `false` otherwise.
     */
    fun updateCategory(categoryId: Int, updatedCategory: ArticleCategory): Boolean

    /**
     * Deletes a category from the repository.
     *
     * @param categoryId The ID of the category to be deleted.
     * @return `true` if the deletion is successful, `false` otherwise.
     */
    fun deleteCategory(categoryId: Int): Boolean

    /**
     * Retrieves a category from the repository by its ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return The retrieved category object if found, or `null` if not found.
     */
    fun getCategory(categoryId: Int): ArticleCategory?

    /**
     * Retrieves all categories stored in the repository.
     *
     * @return A list containing all categories.
     */
    fun getAllCategory(): List<ArticleCategory>
}
