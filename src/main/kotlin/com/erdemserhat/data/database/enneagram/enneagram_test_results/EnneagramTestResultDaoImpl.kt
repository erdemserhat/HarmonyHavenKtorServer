package com.erdemserhat.data.database.enneagram.enneagram_test_results

import com.erdemserhat.data.database.DatabaseConfig
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.filter
import org.ktorm.entity.first
import org.ktorm.entity.map
import org.ktorm.entity.sequenceOf

class EnneagramTestResultDaoImpl: EnneagramTestResultDao {
    override suspend fun getTestResultByUserId(userId: Int): EnneagramTestResultsDto {
        val entity = DatabaseConfig.ktormDatabase.sequenceOf(DBEnneagramTestResultsTable)
            .first { it.userId eq userId }

        return EnneagramTestResultsDto(
            id = entity.id,
            userId = entity.userId,
            typeOneScore = entity.typeOneScore,
            typeTwoScore = entity.typeTwoScore,
            typeThreeScore = entity.typeThreeScore,
            typeFourScore = entity.typeFourScore,
            typeFiveScore = entity.typeFiveScore,
            typeSixScore = entity.typeSixScore,
            typeSevenScore = entity.typeSevenScore,
            typeEightScore = entity.typeEightScore,
            typeNineScore = entity.typeNineScore,
            dominantType = entity.dominantType,
            createdAt = entity.createdAt
        )
    }


    override suspend fun addTestResult(testResult: EnneagramTestResultsDto) {
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