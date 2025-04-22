package com.erdemserhat.data.database.enneagram.enneagram_answers

import com.erdemserhat.data.database.enneagram.enneagram_questions.DBEnneagramQuestionTable.bindTo
import com.erdemserhat.data.database.enneagram.enneagram_questions.DBEnneagramQuestionTable.primaryKey
import com.erdemserhat.data.database.user.DBUserTable
import io.ktor.server.util.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.security.Timestamp
import java.time.LocalDateTime
import java.util.*

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
data class EnneagramAnswersDto(
    val userId:Int,
    val questionId:Int,
    val score:Int,
    @Contextual val date: Date
){
    init {
        require(score in 0..3) { "Score must be between 0 and 3. Given: $score" }
    }
}
