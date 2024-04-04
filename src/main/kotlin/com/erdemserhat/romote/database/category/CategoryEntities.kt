package com.erdemserhat.romote.database.category

import com.erdemserhat.romote.database.user.DBUserEntity
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * Represents the database table for categories.
 */
object DBCategoryTable : Table<DBCategoryEntity>("categories") {
    // Define the columns of the table
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val imagePath = varchar("image_path").bindTo { it.imagePath }
}

/**
 * Represents a category entity in the database.
 */
interface DBCategoryEntity : Entity<DBCategoryEntity> {
    companion object : Entity.Factory<DBUserEntity>()

    val id: Int
    val name: String
    val imagePath: String
}
