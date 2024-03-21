package com.erdemserhat.database.categoryDao

import com.erdemserhat.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.models.Category
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList



class CategoryDaoImpl: CategoryDao {
    override fun addCategory(category: Category): Int {

        return ktormDatabase.insert(DBCategoryTable){
            set(it.name,category.name)
            set(it.imagePath,category.imagePath)
        }
    }

    override fun updateCategory(categoryId: Int, updatedCategory: Category):Boolean {

        try {
            ktormDatabase.update(DBCategoryTable){
                set(it.name,updatedCategory.name)
                set(it.imagePath,updatedCategory.imagePath)

                where {
                    it.id eq categoryId
                }
            }
            return true
        } catch (e: Exception) {
           return false
        }

    }

    override fun deleteCategory(categoryId: Int): Boolean {
        val affectedRows = ktormDatabase.delete(DBCategoryTable) {
            it.id eq categoryId
        }
        return affectedRows > 0
    }

    override fun getCategory(categoryId: Int): DBCategoryEntity? {
        return ktormDatabase.sequenceOf(DBCategoryTable)
            .firstOrNull{it.id eq categoryId}
    }

    override fun getAllCategory(): List<DBCategoryEntity> {
        return ktormDatabase.sequenceOf(DBCategoryTable).toList()
    }
}