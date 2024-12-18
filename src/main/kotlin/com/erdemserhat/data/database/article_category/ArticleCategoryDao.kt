package com.erdemserhat.data.database.article_category

import com.erdemserhat.models.ArticleCategory

/**
 * Interface defining operations for interacting with category data in the database.
 */
interface ArticleCategoryDao {
    /**
     * Adds a new category to the database.
     * @param category The category to add.
     * @return The ID of the added category.
     */
    suspend fun addCategory(category: ArticleCategory): Int

    /**
     * Updates an existing category in the database.
     * @param categoryId The ID of the category to update.
     * @param updatedCategory The updated category data.
     * @return True if the category was successfully updated, false otherwise.
     */
    suspend fun updateCategory(categoryId: Int, updatedCategory: ArticleCategory): Boolean

    /**
     * Deletes a category from the database.
     * @param categoryId The ID of the category to delete.
     * @return True if the category was successfully deleted, false otherwise.
     */
    suspend fun deleteCategory(categoryId: Int): Boolean

    /**
     * Retrieves a category from the database by its ID.
     * @param categoryId The ID of the category to retrieve.
     * @return The category entity if found, null otherwise.
     */
    suspend fun getCategory(categoryId: Int): DBArticleCategoryEntity?

    /**
     * Retrieves all categories from the database.
     * @return A list of all categories in the database.
     */
    suspend fun getAllCategory(): List<DBArticleCategoryEntity>
}
