package com.erdemserhat.data.database.sql.article_category

import com.erdemserhat.data.database.sql.MySqlDatabaseConfig.ktormDatabase
import com.erdemserhat.models.ArticleCategory
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList


/**
 * Implementation of [ArticleCategoryDao] interface for interacting with category data in the database.
 */
class ArticleCategoryDaoImpl : ArticleCategoryDao {
    override suspend fun addCategory(category: ArticleCategory): Int {
        // Insert a new category into the database and return its ID
        return ktormDatabase.insert(DBArticleCategoryTable) {
            set(DBArticleCategoryTable.name, category.name)
            set(DBArticleCategoryTable.imagePath, category.imagePath)
        }
    }

    override suspend fun updateCategory(categoryId: Int, updatedCategory: ArticleCategory): Boolean {
        // Update an existing category in the database
        try {
            ktormDatabase.update(DBArticleCategoryTable) {
                set(DBArticleCategoryTable.name, updatedCategory.name)
                set(DBArticleCategoryTable.imagePath, updatedCategory.imagePath)
                where {
                    DBArticleCategoryTable.id eq categoryId
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteCategory(categoryId: Int): Boolean {
        // Delete a category from the database
        val affectedRows = ktormDatabase.delete(DBArticleCategoryTable) {
            DBArticleCategoryTable.id eq categoryId
        }
        return affectedRows > 0
    }

    override suspend fun getCategory(categoryId: Int): DBArticleCategoryEntity? {
        // Retrieve a category from the database by its ID
        return ktormDatabase.sequenceOf(DBArticleCategoryTable)
            .firstOrNull { DBArticleCategoryTable.id eq categoryId }
    }

    override suspend fun getAllCategory(): List<DBArticleCategoryEntity> {
        // Retrieve all categories from the database
        return ktormDatabase.sequenceOf(DBArticleCategoryTable).toList()
    }
}
