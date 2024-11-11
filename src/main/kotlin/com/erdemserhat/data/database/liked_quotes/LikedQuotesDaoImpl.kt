package com.erdemserhat.data.database.liked_quotes

import com.erdemserhat.data.database.DatabaseConfig
import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.data.database.article.DBArticleTable
import com.erdemserhat.data.database.article_category.DBArticleCategoryTable
import com.erdemserhat.data.database.quote_category.DBQuoteCategoryTable
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class LikedQuotesDaoImpl : LikedQuotesDao {
    override suspend fun likeQuote(userId: Int, quoteId: Int): Boolean {
        val operation = ktormDatabase.insert(DBLikedQuoteTable) {
            set(DBLikedQuoteTable.userId, userId)
            set(DBLikedQuoteTable.quoteId, quoteId)
        }
        return operation > 0
    }

    override suspend fun unLikeQuote(userId: Int, quoteId: Int): Boolean {
        val affectedRows = ktormDatabase.delete(DBLikedQuoteTable) {
            (DBLikedQuoteTable.quoteId eq quoteId) and (DBLikedQuoteTable.userId eq userId)
        }
        return affectedRows > 0
    }

    override suspend fun isUserLikedQuote(userId: Int, quoteId: Int): Boolean {
        val result = ktormDatabase.from(DBLikedQuoteTable)
            .select()
            .where {
                (DBLikedQuoteTable.quoteId eq quoteId) and (DBLikedQuoteTable.userId eq userId)
            }.totalRecords

        return result > 0


    }

    override suspend fun getAllLikedQuotesOfUser(userId: Int): List<Int> {
        val result = DatabaseConfig.ktormDatabase.from(DBLikedQuoteTable)
            .select(DBLikedQuoteTable.quoteId)
            .where { DBLikedQuoteTable.userId eq userId }
        return result.map { it[DBLikedQuoteTable.quoteId] as Int }
    }
}