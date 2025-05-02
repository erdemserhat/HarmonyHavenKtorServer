package com.erdemserhat.data.database.sql.liked_comment

import com.erdemserhat.data.database.sql.comment.DBCommentEntity
import com.erdemserhat.data.database.sql.comment.DBCommentTable
import com.erdemserhat.data.database.sql.user.DBUserEntity
import com.erdemserhat.data.database.sql.user.DBUserTable
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int


object DBLikedCommentTable: Table<DBLikedCommentEntity>("liked_comments"){
    val commentId = int("comment_id").references(DBCommentTable){it.comment}
    val userId = int("user_id").references(DBUserTable) { it.user }
    


}

interface DBLikedCommentEntity: Entity<DBLikedCommentEntity> {
    companion object : Entity.Factory<DBLikedCommentEntity>()
    val comment:DBCommentEntity
    val user:DBUserEntity
}

