package com.erdemserhat.romote.database.quote_category

import com.erdemserhat.romote.database.quote.DBQuoteTable.bindTo
import com.erdemserhat.romote.database.quote.DBQuoteTable.primaryKey
import com.erdemserhat.romote.database.user.DBUserEntity
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object DBQuoteCategoryTable : Table<DBQuoteCategoryEntity>("quote_categories") {
    // Define the columns of the table
    val id = int("id").primaryKey().bindTo { it.id }
    val name=varchar("name").bindTo { it.name }

}

interface DBQuoteCategoryEntity : Entity<DBQuoteCategoryEntity> {
    companion object : Entity.Factory<DBUserEntity>()
    val id: Int
    val name:String
}

