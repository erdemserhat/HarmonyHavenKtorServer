package com.erdemserhat.repository.category

import com.erdemserhat.database.categoryDao.DBCategoryEntity
import com.erdemserhat.models.Category

interface CategoryRepositoryContract {
    fun addCategory(category: Category):Boolean
    fun updateCategory(categoryId:Int, updatedCategory: Category):Boolean
    fun deleteCategory(categoryId:Int):Boolean
    fun getCategory(categoryId:Int): Category?
    fun getAllCategory():List<Category>





}