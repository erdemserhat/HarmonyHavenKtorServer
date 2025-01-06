package com.erdemserhat.data.repository.comments

import com.erdemserhat.data.database.comment.DBCommentEntity
import com.erdemserhat.dto.responses.CommentBaseDto
import com.erdemserhat.dto.responses.CommentsClientDto
import java.time.LocalDateTime

interface CommentRepositoryContract {
    suspend fun addComment(userId:Int,postId: Int, comment: String)
    suspend fun deleteComment(commentId:Int)
    suspend fun getAllComments():List<CommentBaseDto>
    suspend fun getCommentByPostId(postId:Int,userId:Int):List<CommentsClientDto>
    suspend fun getCommentById(id:Int):CommentBaseDto?
}

