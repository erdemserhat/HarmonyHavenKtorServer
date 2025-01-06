package com.erdemserhat.service.commentservice

import com.erdemserhat.dto.responses.CommentsClientDto
import com.erdemserhat.service.di.DatabaseModule
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CommentService:CommentServiceContract {
    private val commentRepo = DatabaseModule.commentRepository
    private val likedCommentRepo = DatabaseModule.likedCommentRepository

    override suspend fun addComment(postId: Int, userId: Int, comment: String) {
        //TODO:Before adding comment check the comment content if it is appropriate
        commentRepo.addComment(
            comment = comment,
            userId = userId,
            postId = postId
        )

    }

    override suspend fun deleteComment(commentId: Int, userId: Int) {
        val comment = commentRepo.getCommentById(commentId)
        if (comment == null) {
            //comment already deleted
            return
        } else {
            val isUserOwnerOfComment = comment.authorId == userId
            if (isUserOwnerOfComment) {
                likedCommentRepo.deleteLikeRecord(commentId)
                commentRepo.deleteComment(commentId)
            } else {
                //comment does not belong to user
                //return unauthorized response
                return
            }
        }

    }

    override suspend fun getCommentsByPostId(postId: Int, userId:Int): List<CommentsClientDto> {
        val commentsClientDto = commentRepo.getCommentByPostId(postId,userId).map {
            CommentsClientDto(
                id = it.id,
                date = formatTimeAgo(it.date),
                author = it.author,
                content = it.content,
                isLiked = it.isLiked,
                likeCount = it.likeCount,
                authorProfilePictureUrl = it.authorProfilePictureUrl,
                hasOwnership =it.hasOwnership
            )
        }

        return commentsClientDto



    }

    override suspend fun likeComment(commentId: Int, userId: Int) {
        likedCommentRepo.likeComment(commentId, userId)

    }

    override suspend fun unlikeComment(commentId: Int, userId: Int) {
        likedCommentRepo.unLikeComment(commentId, userId)
    }

    fun formatTimeAgo(dateString: String): String {
        // Doğru tarih formatı
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val givenDate = LocalDateTime.parse(dateString, formatter)

        // Şu anki tarih
        val now = LocalDateTime.now(ZoneId.systemDefault())

        // İki tarih arasındaki süreyi hesapla
        val duration = Duration.between(givenDate, now)

        return when {
            duration.toMillis() < 0 -> "Gelecek tarih" // Gelecek tarihler için
            duration.toMinutes() < 1 -> "şimdi"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} dk önce"
            duration.toHours() < 24 -> "${duration.toHours()} s önce"
            duration.toDays() < 7 -> "${duration.toDays()} g önce"
            duration.toDays() < 30 -> "${duration.toDays() / 7} hafta önce"
            duration.toDays() < 365 -> "${duration.toDays() / 30} ay önce"
            else -> "${duration.toDays() / 365} yıl önce"
        }
    }


}
