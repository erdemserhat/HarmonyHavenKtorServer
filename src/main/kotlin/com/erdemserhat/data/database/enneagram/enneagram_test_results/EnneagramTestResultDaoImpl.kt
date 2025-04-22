package com.erdemserhat.data.database.enneagram.enneagram_test_results

import com.erdemserhat.data.database.DatabaseConfig
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.first
import org.ktorm.entity.sequenceOf

class EnneagramTestResultDaoImpl: EnneagramTestResultDao {
    override suspend fun getTestResultByUserId(userId: Int): EnneagramTestResultDto {
        val entity = DatabaseConfig.ktormDatabase.sequenceOf(DBEnneagramTestResultsTable)
            .first { it.userId eq userId }

        return entity.toDto()
    }


    override suspend fun addTestResult(testResult: EnneagramTestResultDto) {
        DatabaseConfig.ktormDatabase.insert(DBEnneagramTestResultsTable) {
            set(it.userId, testResult.userId)
            set(it.typeOneScore, testResult.typeOneScore)
            set(it.typeTwoScore, testResult.typeTwoScore)
            set(it.typeThreeScore, testResult.typeThreeScore)
            set(it.typeFourScore, testResult.typeFourScore)
            set(it.typeFiveScore, testResult.typeFiveScore)
            set(it.typeSixScore, testResult.typeSixScore)
            set(it.typeSevenScore, testResult.typeSevenScore)
            set(it.typeEightScore, testResult.typeEightScore)
            set(it.typeNineScore, testResult.typeNineScore)
            set(it.dominantType, testResult.dominantType)
            set(it.createdAt, testResult.createdAt)
        }
    }
}
