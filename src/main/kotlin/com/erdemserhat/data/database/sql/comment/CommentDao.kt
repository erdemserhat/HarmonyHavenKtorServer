package com.erdemserhat.data.database.sql.comment

import com.erdemserhat.dto.responses.CommentsClientDto

interface CommentDao {
    suspend fun addComment(userId:Int,postId: Int, comment: String)
    suspend fun deleteComment(commentId:Int)
    suspend fun getAllComments():List<DBCommentEntity>
    suspend fun getCommentByPostId(postId:Int,userId: Int):List<CommentsClientDto>
    suspend fun getCommentById(id:Int):DBCommentEntity?

}