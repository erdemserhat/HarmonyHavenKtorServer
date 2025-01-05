package com.erdemserhat.data.database.comment

interface CommentDao {
    suspend fun addComment(userId:Int,postId: Int, comment: String)
    suspend fun deleteComment(commentId:Int)
    suspend fun getAllComments(postId:Int):List<DBCommentEntity>
    suspend fun getCommentByPostId(postId:Int):List<DBCommentEntity>

}