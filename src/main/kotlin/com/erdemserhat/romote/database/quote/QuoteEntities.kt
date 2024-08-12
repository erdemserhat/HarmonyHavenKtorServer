package com.erdemserhat.romote.database.quote

import com.erdemserhat.romote.database.article_category.DBArticleCategoryEntity
import com.erdemserhat.romote.database.article_category.DBArticleCategoryTable.bindTo
import com.erdemserhat.romote.database.article_category.DBArticleCategoryTable.primaryKey
import com.erdemserhat.romote.database.user.DBUserEntity
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

}

interface DBQuoteEntity : Entity<DBQuoteEntity> {
    companion object : Entity.Factory<DBUserEntity>()
    val id: Int
    val quote: String
    val writer: String

}

