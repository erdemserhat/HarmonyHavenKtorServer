package com.erdemserhat.data.database.enneagram.enneagram_answers

import com.erdemserhat.data.database.comment.DBCommentEntity
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

interface EnneagramAnswerDao {
    suspend fun addAnswers(answers:List<EnneagramAnswersDto>)
    suspend fun getAnswersByUserId(userId:Int):List<DBEnneagramAnswersEntity>
    suspend fun deleteAnswersByUserId(userId:Int)
}

