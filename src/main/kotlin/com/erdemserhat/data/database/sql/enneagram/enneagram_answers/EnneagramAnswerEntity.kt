package com.erdemserhat.data.database.sql.enneagram.enneagram_answers

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.LocalDateTime

object DBEnneagramAnswersTable : Table<DBEnneagramAnswersEntity>("enneagram_answers") {
    val id = int("id").primaryKey().bindTo { it.id }
    val userId = int("user_id").bindTo { it.userId }
    val questionId = int("question_id").bindTo { it.questionId }
    val score = int("score").bindTo { it.score }
    val createdAt = datetime("created_at").bindTo { it.createdAt }
}

interface DBEnneagramAnswersEntity : Entity<DBEnneagramAnswersEntity> {
    companion object : Entity.Factory<DBEnneagramAnswersEntity>()
    val id: Int
    val userId: Int
    val questionId: Int
    val score:Int
    val createdAt: LocalDateTime

}

@Serializable
data class EnneagramAnswerDto(
    @Contextual val questionId:ObjectId,
    val score:Int,
){
    init {
        require(score in 0..3) { "Score must be between 0 and 3. Given: $score" }
    }
}

