package com.erdemserhat.data.repository.liked_quote

import com.erdemserhat.data.database.sql.liked_quotes.LikedQuotesDao
import com.erdemserhat.data.database.sql.liked_quotes.LikedQuotesDaoImpl

class LikedQuoteRepository : LikedQuoteRepositoryContract {
    private val likedQuotesDao: LikedQuotesDao = LikedQuotesDaoImpl()
    override suspend fun likeQuote(userId: Int, quoteId: Int): Boolean {
        return likedQuotesDao.likeQuote(userId, quoteId)
    }

    override suspend fun removeLike(userId: Int, quoteId: Int): Boolean {
        return likedQuotesDao.unLikeQuote(userId, quoteId)
    }

    override suspend fun isUserLikedQuote(userId: Int, quoteId: Int): Boolean {
        return likedQuotesDao.isUserLikedQuote(userId, quoteId)
    }

    override suspend fun getAllLikedQuotesOfUser(userId: Int): List<Int> {
        return likedQuotesDao.getAllLikedQuotesOfUser(userId)
    }
}
