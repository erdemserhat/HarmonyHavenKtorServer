package com.erdemserhat.data.database.liked_quotes
import com.erdemserhat.data.database.article_category.DBArticleCategoryTable.bindTo
import com.erdemserhat.data.database.article_category.DBArticleCategoryTable.primaryKey
import com.erdemserhat.data.database.user.DBUserEntity
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBLikedQuoteTable:Table<DBLikedQuoteEntity>("liked_quote"){
    val userId = int("user_id").bindTo { it.userId }
    val quoteId = int("quote_id").bindTo { it.quoteId }
}

interface DBLikedQuoteEntity: Entity<DBLikedQuoteEntity>{
    companion object :Entity.Factory<DBLikedQuoteEntity>()
    val userId : Int
    val quoteId : Int

}