package com.erdemserhat.database.categoryDao

import com.erdemserhat.database.userDao.DBUserEntity
import com.erdemserhat.database.userDao.DBUserTable.bindTo
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
object DBCategoryTable : Table<DBCategoryEntity>("Categories"){

    val id= int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    var imagePath=varchar("image_path").bindTo { it.imagePath }

}

interface DBCategoryEntity :Entity<DBCategoryEntity>{
    companion object :  Entity.Factory<DBUserEntity>()

    val id:Int
    val name:String
    val imagePath:String
}