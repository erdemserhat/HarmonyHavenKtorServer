package com.erdemserhat.data.database.comment

import com.erdemserhat.data.database.liked_quotes.DBLikedQuoteTable.bindTo
import com.erdemserhat.data.database.quote.DBQuoteEntity
import com.erdemserhat.data.database.quote.DBQuoteTable
import com.erdemserhat.data.database.user.DBUserEntity
import com.erdemserhat.data.database.user.DBUserTable
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDateTime
import java.util.*

object DBCommentTable : Table<DBCommentEntity>("comments") {
    val id = int("id").primaryKey().bindTo { it.id }
    val authorId = int("author_id").references(DBUserTable) { it.authorId}
    val postId = int("post_id").references(DBQuoteTable) { it.postId }
    val content = varchar("content").bindTo { it.content }
    val date = datetime("date").bindTo { it.date }
}

interface DBCommentEntity : Entity<DBCommentEntity> {
    companion object : Entity.Factory<DBCommentEntity>()

    val id: Int
    val authorId: DBUserEntity // Linked to the `authorId` foreign key
    val postId: DBQuoteEntity  // Linked to the `postId` foreign key
    var content: String
    var date: LocalDateTime

    // Use extension functions to get the actual related entities (DBUserEntity and DBQuoteEntity)
    val author: DBUserEntity
        get() = authorId // Access the related DBUserEntity using authorId

    val post: DBQuoteEntity
        get() = postId // Access the related DBQuoteEntity using postId
}




