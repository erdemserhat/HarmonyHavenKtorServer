package com.erdemserhat.service.commentservice

import com.erdemserhat.dto.responses.CommentsClientDto

interface CommentServiceContract {
    suspend fun addComment(postId: Int, userId: Int, comment: String)
    suspend fun deleteComment(commentId: Int, userId: Int)
    suspend fun getCommentsByPostId(postId: Int,userId:Int): List<CommentsClientDto>
    suspend fun likeComment(commentId: Int, userId: Int)
    suspend fun unlikeComment(commentId: Int, userId: Int)

}