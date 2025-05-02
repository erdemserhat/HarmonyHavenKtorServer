package com.erdemserhat.data.database.sql.liked_quotes

import com.erdemserhat.data.database.sql.MySqlDatabaseConfig.ktormDatabase
import org.ktorm.dsl.*

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
        val result = ktormDatabase.from(DBLikedQuoteTable)
            .select(DBLikedQuoteTable.quoteId)
            .where { DBLikedQuoteTable.userId eq userId }
        return result.map { it[DBLikedQuoteTable.quoteId] as Int }
    }
}