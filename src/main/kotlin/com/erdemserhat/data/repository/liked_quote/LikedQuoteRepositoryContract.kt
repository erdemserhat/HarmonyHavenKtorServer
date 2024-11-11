package com.erdemserhat.data.repository.liked_quote

interface LikedQuoteRepositoryContract {


    suspend fun likeQuote(userId: Int, quoteId:Int):Boolean
    suspend fun removeLike(userId: Int, quoteId:Int):Boolean
    suspend fun isUserLikedQuote(userId: Int, quoteId:Int):Boolean
    suspend fun getAllLikedQuotesOfUser(userId: Int): List<Int>


}
