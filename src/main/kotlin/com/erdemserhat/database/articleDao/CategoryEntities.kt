package com.erdemserhat.database.articleDao

import com.erdemserhat.database.categoryDao.DBCategoryTable.bindTo
import com.erdemserhat.database.categoryDao.DBCategoryTable.primaryKey
import com.erdemserhat.database.userDao.DBUserEntity
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBArticleTable : Table<DBArticleEntity>("Articles"){
//id	title	content	publish_date	category_id	image_path
    val id= int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val content=varchar("content").bindTo { it.content }
    val publishDate = varchar("publish_date").bindTo { it.publishDate }
    val categoryId = int("category_id").bindTo { it.categoryId }
    val imagePath = varchar("image_path").bindTo { it.imagePath }

}

interface DBArticleEntity : Entity<DBArticleEntity> {
    companion object :  Entity.Factory<DBUserEntity>()

    val id:Int
    val title:String
    val content:String
    val publishDate:String
    val categoryId:Int
    val imagePath:String
}