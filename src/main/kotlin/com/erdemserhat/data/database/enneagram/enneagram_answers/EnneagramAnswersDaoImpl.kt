package com.erdemserhat.data.database.enneagram.enneagram_answers

import com.erdemserhat.data.database.DatabaseConfig
import io.ktor.server.util.*
import io.ktor.utils.io.*
import org.ktorm.dsl.delete
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.filter
import org.ktorm.entity.map
import java.time.LocalDateTime


class EnneagramAnswersDaoImpl: EnneagramAnswerDao {
    @OptIn(InternalAPI::class)
    override suspend fun addAnswers(answers: List<EnneagramAnswersDto>,userId:Int) {
        val date = LocalDateTime.now()
        answers.forEach { answer -> // answer burada EnneagramAnswersDto nesnesi
            DatabaseConfig.ktormDatabase.insert(DBEnneagramAnswersTable) {
                set(it.userId, userId)
                set(it.questionId, answer.questionId)
                set(it.score, answer.score)
                set(it.createdAt, date)
            }
        }
    }

    override suspend fun getAnswersByUserId(userId: Int): List<EnneagramAnswersDto> {
       val answers = DatabaseConfig.ktormDatabase.sequenceOf(DBEnneagramAnswersTable).filter {answerRow->
           answerRow.userId eq userId

       }.map { answerRow->
           answerRow.toDto()

       }
        return answers.toList()
    }

    override suspend fun deleteAnswersByUserId(userId: Int) {
        DatabaseConfig.ktormDatabase.delete(DBEnneagramAnswersTable){
            it.userId eq userId
        }
    }

}