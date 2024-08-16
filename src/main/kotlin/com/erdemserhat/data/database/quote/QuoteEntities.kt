package com.erdemserhat.data.database.quote

import com.erdemserhat.data.database.user.DBUserEntity
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object DBQuoteTable : Table<DBQuoteEntity>("quotes") {
    // Define the columns of the table
    val id = int("id").primaryKey().bindTo { it.id }
    val quote = text("quote").bindTo { it.quote }
    val writer = varchar("writer").bindTo { it.writer }
    val imageUrl = varchar("image_url").bindTo { it.imageUrl }

}

interface DBQuoteEntity : Entity<DBQuoteEntity> {
    companion object : Entity.Factory<DBUserEntity>()
    val id: Int
    val quote: String
    val writer: String
    val imageUrl: String

}

