package com.erdemserhat.data.repository.liked_comments

import com.erdemserhat.data.database.sql.liked_comment.LikedCommentDao
import com.erdemserhat.data.database.sql.liked_comment.LikedCommentImpl

class LikedCommentsRepository:LikedCommentsRepositoryContract {
    private val likedCommentDao:LikedCommentDao = LikedCommentImpl()

    override suspend fun likeComment(commentId: Int, userId: Int): Int {
        return likedCommentDao.likeComment(commentId, userId)

    }

    override suspend fun unLikeComment(commentId: Int, userId: Int) {
        return likedCommentDao.unLikeComment(commentId, userId)
    }

    override suspend fun checkIsLiked(commentId: Int, userId: Int): Boolean {
        return likedCommentDao.checkIsLiked(commentId, userId)
    }

    override suspend fun getLikeCount(commentId: Int): Int {
        return likedCommentDao.getLikeCount(commentId)
    }

    override suspend fun deleteLikeRecord(commentId: Int) {
        return likedCommentDao.deleteLikeRecord(commentId)
    }

}