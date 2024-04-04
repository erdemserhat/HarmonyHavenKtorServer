package com.erdemserhat.romote.database.category

import com.erdemserhat.romote.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.models.Category
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList


/**
 * Implementation of [CategoryDao] interface for interacting with category data in the database.
 */
class CategoryDaoImpl : CategoryDao {
    override fun addCategory(category: Category): Int {
        // Insert a new category into the database and return its ID
        return ktormDatabase.insert(DBCategoryTable) {
            set(DBCategoryTable.name, category.name)
            set(DBCategoryTable.imagePath, category.imagePath)
        }
    }

    override fun updateCategory(categoryId: Int, updatedCategory: Category): Boolean {
        // Update an existing category in the database
        try {
            ktormDatabase.update(DBCategoryTable) {
                set(DBCategoryTable.name, updatedCategory.name)
                set(DBCategoryTable.imagePath, updatedCategory.imagePath)
                where {
                    DBCategoryTable.id eq categoryId
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun deleteCategory(categoryId: Int): Boolean {
        // Delete a category from the database
        val affectedRows = ktormDatabase.delete(DBCategoryTable) {
            DBCategoryTable.id eq categoryId
        }
        return affectedRows > 0
    }

    override fun getCategory(categoryId: Int): DBCategoryEntity? {
        // Retrieve a category from the database by its ID
        return ktormDatabase.sequenceOf(DBCategoryTable)
            .firstOrNull { DBCategoryTable.id eq categoryId }
    }

    override fun getAllCategory(): List<DBCategoryEntity> {
        // Retrieve all categories from the database
        return ktormDatabase.sequenceOf(DBCategoryTable).toList()
    }
}
