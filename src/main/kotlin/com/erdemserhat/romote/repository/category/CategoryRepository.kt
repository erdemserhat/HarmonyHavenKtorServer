package com.erdemserhat.romote.repository.category

import com.erdemserhat.romote.database.category.CategoryDao
import com.erdemserhat.romote.database.category.CategoryDaoImpl
import com.erdemserhat.romote.database.category.DBCategoryEntity
import com.erdemserhat.models.Category

/**
 * Repository responsible for managing category data operations.
 * Implements [CategoryRepositoryContract] interface.
 */
class CategoryRepository : CategoryRepositoryContract {

    private val categoryDao: CategoryDao = CategoryDaoImpl()

    /**
     * Adds a new category to the database.
     * @param category The category to be added.
     * @return `true` if the category is added successfully, `false` otherwise.
     */
    override fun addCategory(category: Category): Boolean {
        return categoryDao.addCategory(category) > 0
    }

    /**
     * Updates an existing category in the database.
     * @param categoryId The ID of the category to be updated.
     * @param updatedCategory The updated category information.
     * @return `true` if the category is updated successfully, `false` otherwise.
     */
    override fun updateCategory(categoryId: Int, updatedCategory: Category): Boolean {
        return categoryDao.updateCategory(categoryId, updatedCategory)
    }

    /**
     * Deletes a category from the database.
     * @param categoryId The ID of the category to be deleted.
     * @return `true` if the category is deleted successfully, `false` otherwise.
     */
    override fun deleteCategory(categoryId: Int): Boolean {
        return categoryDao.deleteCategory(categoryId)
    }

    /**
     * Retrieves a category from the database by its ID.
     * @param categoryId The ID of the category to be retrieved.
     * @return The retrieved category, or `null` if not found.
     */
    override fun getCategory(categoryId: Int): Category? {
        return categoryDao.getCategory(categoryId)?.toCategory()
    }

    /**
     * Retrieves all categories from the database.
     * @return A list of all categories.
     */
    override fun getAllCategory(): List<Category> {
        return categoryDao.getAllCategory().map { it.toCategory() }
    }
}

/**
 * Extension function to convert a [DBCategoryEntity] to a [Category].
 * @return The converted category object.
 */
fun DBCategoryEntity.toCategory(): Category {
    return Category(
        id, name, imagePath
    )
}
