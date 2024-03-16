package com.erdemserhat.database.categoryDao

import com.erdemserhat.database.userDao.DBUserEntity
import com.erdemserhat.models.Category

interface CategoryDao {

    fun addCategory(category: Category):Int
    fun updateCategory(categoryId:Int, updatedCategory:Category):Boolean
    fun deleteCategory(categoryId:Int):Boolean
    fun getCategory(categoryId:Int):DBCategoryEntity?
    fun getAllCategory():List<DBCategoryEntity>

}