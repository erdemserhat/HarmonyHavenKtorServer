package com.erdemserhat.data.database.liked_quotes

import com.erdemserhat.models.Quote

interface LikedQuotesDao {
    suspend fun likeQuote(userId: Int, quoteId:Int):Boolean
    suspend fun unLikeQuote(userId: Int, quoteId:Int):Boolean
    suspend fun isUserLikedQuote(userId: Int, quoteId:Int):Boolean
    suspend fun getAllLikedQuotesOfUser(userId: Int): List<Int>
}