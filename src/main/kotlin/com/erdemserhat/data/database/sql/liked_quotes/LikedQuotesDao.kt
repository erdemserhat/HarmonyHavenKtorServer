package com.erdemserhat.data.database.sql.liked_quotes

interface LikedQuotesDao {
    suspend fun likeQuote(userId: Int, quoteId:Int):Boolean
    suspend fun unLikeQuote(userId: Int, quoteId:Int):Boolean
    suspend fun isUserLikedQuote(userId: Int, quoteId:Int):Boolean
    suspend fun getAllLikedQuotesOfUser(userId: Int): List<Int>
}