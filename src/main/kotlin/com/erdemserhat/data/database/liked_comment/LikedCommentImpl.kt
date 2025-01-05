package com.erdemserhat.data.database.liked_comment

import com.erdemserhat.data.database.DatabaseConfig
import org.ktorm.dsl.and
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert

class LikedCommentImpl : LikedCommentDao {
    val database = DatabaseConfig.ktormDatabase
    override suspend fun likeComment(commentId: Int, userId: Int): Int {
        val result = database.insert(DBLikedCommentTable) {
            set(DBLikedCommentTable.commentId, commentId)
            set(DBLikedCommentTable.userId, userId)

        }
        return result
    }

    override suspend fun unLikeComment(commentId: Int, userId: Int) {
        database.delete(DBLikedCommentTable) {
            (it.commentId eq commentId) and (it.userId eq userId)
        }
    }
}