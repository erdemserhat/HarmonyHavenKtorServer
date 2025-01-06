package com.erdemserhat.data.repository.liked_comments

interface LikedCommentsRepositoryContract {
    suspend fun likeComment(commentId:Int,userId:Int):Int
    suspend fun unLikeComment(commentId:Int,userId: Int)
    suspend fun checkIsLiked(commentId:Int,userId: Int):Boolean
    suspend fun getLikeCount(commentId:Int):Int
    suspend fun deleteLikeRecord(commentId:Int)

}