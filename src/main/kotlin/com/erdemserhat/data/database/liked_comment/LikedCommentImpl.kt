package com.erdemserhat.data.database.liked_comment

import com.erdemserhat.data.database.DatabaseConfig
import org.ktorm.dsl.and
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf

class   LikedCommentImpl : LikedCommentDao {
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

    override suspend fun checkIsLiked(commentId: Int, userId: Int): Boolean {
        val likedComment = database.sequenceOf(DBLikedCommentTable)
            .find { (it.commentId eq commentId) and (it.userId eq userId)}

        if(likedComment != null){
            return true
        }else{
            return false
        }

    }

    override suspend fun getLikeCount(commentId: Int):Int {
        return database.sequenceOf(DBLikedCommentTable)
            .filter { it.commentId eq commentId }.totalRecords
    }

    override suspend fun deleteLikeRecord(commentId: Int) {
        database.delete(DBLikedCommentTable){
            DBLikedCommentTable.commentId eq commentId
        }
    }
}