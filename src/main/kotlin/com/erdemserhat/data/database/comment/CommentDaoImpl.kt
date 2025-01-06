package com.erdemserhat.data.database.comment

import com.erdemserhat.data.database.DatabaseConfig
import com.erdemserhat.data.database.DatabaseConfig.ktormDatabase
import com.erdemserhat.data.database.article.DBArticleTable
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
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

    override suspend fun getCommentByPostId(postId: Int): List<DBCommentEntity> {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBCommentTable)
            .filter { DBCommentTable.postId eq postId }.toList()
    }

    override suspend fun getCommentById(id: Int): DBCommentEntity? {
        return DatabaseConfig.ktormDatabase.sequenceOf(DBCommentTable)
            .find { DBCommentTable.id eq id }
    }

}