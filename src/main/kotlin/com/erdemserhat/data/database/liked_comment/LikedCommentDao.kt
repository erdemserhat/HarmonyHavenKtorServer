package com.erdemserhat.data.database.liked_comment

interface LikedCommentDao {
    suspend fun likeComment(commentId:Int,userId:Int):Int
    suspend fun unLikeComment(commentId:Int,userId: Int)
}