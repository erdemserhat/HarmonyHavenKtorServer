package com.erdemserhat.repository.category

import com.erdemserhat.database.categoryDao.CategoryDao
import com.erdemserhat.database.categoryDao.CategoryDaoImpl
import com.erdemserhat.database.categoryDao.DBCategoryEntity
import com.erdemserhat.models.Category

class CategoryRepository(): CategoryRepositoryContract {
    private val categoryDao : CategoryDao = CategoryDaoImpl()
    override fun addCategory(category: Category): Boolean {
        return categoryDao.addCategory(category) >0

    }

    override fun updateCategory(categoryId: Int, updatedCategory: Category): Boolean {
        return categoryDao.updateCategory(categoryId,updatedCategory)
    }

    override fun deleteCategory(categoryId: Int): Boolean {
        return categoryDao.deleteCategory(categoryId)
    }

    override fun getCategory(categoryId: Int): Category? {
        return categoryDao.getCategory(categoryId)?.toCategory()
    }

    override fun getAllCategory(): List<Category> {
        return categoryDao.getAllCategory().map { it.toCategory() }.toList()
    }


}
fun DBCategoryEntity.toCategory():Category{
    return Category(
        id, name, imagePath

    )
}