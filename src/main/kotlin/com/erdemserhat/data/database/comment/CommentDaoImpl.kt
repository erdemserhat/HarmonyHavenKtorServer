package com.erdemserhat.data.database.comment

import com.erdemserhat.data.database.DatabaseConfig
import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.data.database.article.DBArticleTable
import com.erdemserhat.data.database.liked_comment.DBLikedCommentTable
import com.erdemserhat.data.database.user.DBUserTable
import com.erdemserhat.dto.responses.CommentsClientDto
import org.ktorm.dsl.*
import org.ktorm.entity.*
import java.time.LocalDateTime

class CommentDaoImpl: CommentDao {
    override suspend fun addComment(userId: Int,postId: Int, comment: String) {
        ktormDatabase.insert(DBCommentTable){
            set(DBCommentTable.postId,postId)
            set(DBCommentTable.authorId, userId)
            set(DBCommentTable.date, LocalDateTime.now())
            set(DBCommentTable.content,comment)
        }
    }

    override suspend fun deleteComment(commentId: Int,) {
        ktormDatabase.delete(DBCommentTable){
            DBCommentTable.id eq commentId
        }
    }

    override suspend fun getAllComments(): List<DBCommentEntity> {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBCommentTable).toList()
    }

    override suspend fun getCommentById(id: Int): DBCommentEntity? {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBCommentTable)
            .find { DBCommentTable.id eq id }
    }

    override suspend fun getCommentByPostId(postId: Int, userId: Int): List<CommentsClientDto> {
        val sqlQuery = """
        SELECT
            c.id,
            c.date,
            c.content,
            u.name AS authorName,
            u.profilePhotoPath AS authorProfilePic,
            COUNT(l.user_id) AS likeCount,
            CASE WHEN l.user_id = ? THEN TRUE ELSE FALSE END AS isLiked,
            CASE WHEN c.author_id = ? THEN TRUE ELSE FALSE END AS hasOwnership
        FROM
            comments c
        LEFT JOIN liked_comments l ON c.id = l.comment_id
        LEFT JOIN users u ON c.author_id = u.id
        WHERE
            c.post_id = ?
        GROUP BY
            c.id, u.name, u.profilePhotoPath
    """.trimIndent()

        return ktormDatabase
            .useConnection { conn ->
                val statement = conn.prepareStatement(sqlQuery)
                statement.setInt(1, userId) // 'isLiked' için userId
                statement.setInt(2, userId) // 'hasOwnership' için userId
                statement.setInt(3, postId) // 'postId' için postId

                val resultSet = statement.executeQuery()
                mutableListOf<CommentsClientDto>().apply {
                    while (resultSet.next()) {
                        add(
                            CommentsClientDto(
                                id = resultSet.getInt("id"),
                                date = (resultSet.getString("date") ?: ""),
                                author = resultSet.getString("authorName") ?: "",
                                content = resultSet.getString("content") ?: "",
                                isLiked = resultSet.getBoolean("isLiked"),
                                likeCount = resultSet.getInt("likeCount"),
                                authorProfilePictureUrl = resultSet.getString("authorProfilePic") ?: "",
                                hasOwnership = resultSet.getBoolean("hasOwnership")
                            )
                        )
                    }
                }
            }
    }




}
