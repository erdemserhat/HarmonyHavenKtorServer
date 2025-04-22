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


class EnneagramAnswersDaoImpl: EnneagramAnswerDao {
    @OptIn(InternalAPI::class)
    override suspend fun addAnswers(answers: List<EnneagramAnswersDto>) {
        answers.forEach { answer -> // answer burada EnneagramAnswersDto nesnesi
            DatabaseConfig.ktormDatabase.insert(DBEnneagramAnswersTable) {
                set(it.userId, answer.userId)
                set(it.questionId, answer.questionId)
                set(it.score, answer.score)
                set(it.createdAt, answer.date.toLocalDateTime())
            }
        }
    }

    override suspend fun getAnswersByUserId(userId: Int): List<DBEnneagramAnswersEntity> {
       val answers = DatabaseConfig.ktormDatabase.sequenceOf(DBEnneagramAnswersTable).filter {
           DBEnneagramAnswersTable.userId eq userId

       }
        return answers.toList()
    }

    override suspend fun deleteAnswersByUserId(userId: Int) {
        DatabaseConfig.ktormDatabase.delete(DBEnneagramAnswersTable){
            it.userId eq userId
        }
    }

}