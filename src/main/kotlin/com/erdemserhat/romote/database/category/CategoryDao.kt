package com.erdemserhat.romote.database.category

import com.erdemserhat.models.Category

/**
 * Interface defining operations for interacting with category data in the database.
 */
interface CategoryDao {
    /**
     * Adds a new category to the database.
     * @param category The category to add.
     * @return The ID of the added category.
     */
    fun addCategory(category: Category): Int

    /**
     * Updates an existing category in the database.
     * @param categoryId The ID of the category to update.
     * @param updatedCategory The updated category data.
     * @return True if the category was successfully updated, false otherwise.
     */
    fun updateCategory(categoryId: Int, updatedCategory: Category): Boolean

    /**
     * Deletes a category from the database.
     * @param categoryId The ID of the category to delete.
     * @return True if the category was successfully deleted, false otherwise.
     */
    fun deleteCategory(categoryId: Int): Boolean

    /**
     * Retrieves a category from the database by its ID.
     * @param categoryId The ID of the category to retrieve.
     * @return The category entity if found, null otherwise.
     */
    fun getCategory(categoryId: Int): DBCategoryEntity?

    /**
     * Retrieves all categories from the database.
     * @return A list of all categories in the database.
     */
    fun getAllCategory(): List<DBCategoryEntity>
}
