package com.erdemserhat.data.database.enneagram.enneagram_questions

import com.erdemserhat.data.database.quote.DBQuoteEntity
import com.erdemserhat.data.database.quote.DBQuoteTable
import com.erdemserhat.data.database.user.DBUserEntity
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDateTime

object DBEnneagramQuestionTable : Table<DBEnneagramQuestionEntity>("enneagram_questions") {
    val id = int("id").primaryKey().bindTo { it.id }
    val authorId = int("personality_number").bindTo { it.personalityNumber}
    val content = varchar("content").bindTo { it.content }
}

interface DBEnneagramQuestionEntity : Entity<DBEnneagramQuestionEntity> {
    companion object : Entity.Factory<DBEnneagramQuestionEntity>()
    val id: Int
    val personalityNumber: Int // Linked to the `authorId` foreign key
    val content: String  // Linked to the `postId` foreign key
}
