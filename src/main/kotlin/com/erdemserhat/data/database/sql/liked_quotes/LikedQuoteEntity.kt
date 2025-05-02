package com.erdemserhat.data.database.sql.liked_quotes
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int

object DBLikedQuoteTable:Table<DBLikedQuoteEntity>("liked_quote"){
    val userId = int("user_id").bindTo { it.userId }
    val quoteId = int("quote_id").bindTo { it.quoteId }
}

interface DBLikedQuoteEntity: Entity<DBLikedQuoteEntity>{
    companion object :Entity.Factory<DBLikedQuoteEntity>()
    val userId : Int
    val quoteId : Int

}