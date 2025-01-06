package com.erdemserhat.data.repository.comments

import com.erdemserhat.data.database.comment.CommentDao
import com.erdemserhat.data.database.comment.CommentDaoImpl
import com.erdemserhat.data.database.comment.DBCommentEntity
import com.erdemserhat.data.database.quote.DBQuoteEntity
import com.erdemserhat.data.database.user.DBUserEntity
import com.erdemserhat.dto.responses.CommentBaseDto
import java.time.LocalDate
import java.time.LocalDateTime

class CommentRepository:CommentRepositoryContract {
    private val commentDao:CommentDao = CommentDaoImpl()

    override suspend fun addComment(userId: Int, postId: Int, comment: String) {
        commentDao.addComment(
            userId = userId,
            postId = postId,
            comment = comment
        )
    }

    override suspend fun deleteComment(commentId: Int) {
        commentDao.deleteComment(
            commentId = commentId
        )
    }

    override suspend fun getAllComments(): List<CommentBaseDto> {
        val convertedList = commentDao.getAllComments().map { commentBaseDto ->
            CommentBaseDto(
                id = commentBaseDto.id,
                authorId = commentBaseDto.author.id,
                authorName = commentBaseDto.author.name,
                postId = commentBaseDto.post.id,
                authorProfilePic = commentBaseDto.author.profilePhotoPath,
                content = commentBaseDto.content,
                date = commentBaseDto.date,
            )
        }

        return convertedList

    }

    override suspend fun getCommentByPostId(postId: Int): List<CommentBaseDto> {
        val convertedList = commentDao.getCommentByPostId(postId).map { commentBaseDto ->
            CommentBaseDto(
                id = commentBaseDto.id,
                authorId = commentBaseDto.author.id,
                authorName = commentBaseDto.author.name,
                authorProfilePic = commentBaseDto.author.profilePhotoPath,
                postId = commentBaseDto.post.id,
                content = commentBaseDto.content,
                date = commentBaseDto.date,
            )
        }

        return convertedList

    }

    override suspend fun getCommentById(id: Int): CommentBaseDto? {
        val comment = commentDao.getCommentById(id)
        if (comment != null){
            return CommentBaseDto(
                id = comment.id,
                authorId = comment.author.id,
                authorName = comment.author.name,
                postId = comment.post.id,
                authorProfilePic = comment.author.profilePhotoPath,
                content = comment.content,
                date = comment.date,
            )
        }else{
            return null
        }


    }
}
