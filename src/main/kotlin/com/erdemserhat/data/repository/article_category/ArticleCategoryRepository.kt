package com.erdemserhat.data.repository.article_category

import com.erdemserhat.data.database.article_category.ArticleCategoryDao
import com.erdemserhat.data.database.article_category.ArticleCategoryDaoImpl
import com.erdemserhat.data.database.article_category.DBArticleCategoryEntity
import com.erdemserhat.models.ArticleCategory

/**
 * Repository responsible for managing category data operations.
 * Implements [ArticleCategoryRepositoryContract] interface.
 */
class ArticleCategoryRepository : ArticleCategoryRepositoryContract {

    private val categoryDao: ArticleCategoryDao = ArticleCategoryDaoImpl()

    /**
     * Adds a new category to the database.
     * @param category The category to be added.
     * @return `true` if the category is added successfully, `false` otherwise.
     */
    override suspend fun addCategory(category: ArticleCategory): Boolean {
        return categoryDao.addCategory(category) > 0
    }

    /**
     * Updates an existing category in the database.
     * @param categoryId The ID of the category to be updated.
     * @param updatedCategory The updated category information.
     * @return `true` if the category is updated successfully, `false` otherwise.
     */
    override suspend fun updateCategory(categoryId: Int, updatedCategory: ArticleCategory): Boolean {
        return categoryDao.updateCategory(categoryId, updatedCategory)
    }

    /**
     * Deletes a category from the database.
     * @param categoryId The ID of the category to be deleted.
     * @return `true` if the category is deleted successfully, `false` otherwise.
     */
    override suspend fun deleteCategory(categoryId: Int): Boolean {
        return categoryDao.deleteCategory(categoryId)
    }

    /**
     * Retrieves a category from the database by its ID.
     * @param categoryId The ID of the category to be retrieved.
     * @return The retrieved category, or `null` if not found.
     */
    override suspend fun getCategory(categoryId: Int): ArticleCategory? {
        return categoryDao.getCategory(categoryId)?.toCategory()
    }

    /**
     * Retrieves all categories from the database.
     * @return A list of all categories.
     */
    override suspend fun getAllCategory(): List<ArticleCategory> {
        return categoryDao.getAllCategory().map { it.toCategory() }
    }
}

/**
 * Extension function to convert a [DBCategoryEntity] to a [ArticleCategory].
 * @return The converted category object.
 */
fun DBArticleCategoryEntity.toCategory(): ArticleCategory {
    return ArticleCategory(
        id, name, imagePath
    )
}
