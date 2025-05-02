package com.erdemserhat.data.database.sql.enneagram.enneagram_test_results

import com.erdemserhat.data.database.sql.MySqlDatabaseConfig
import com.erdemserhat.data.database.sql.MySqlDatabaseConfig.ktormDatabase
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf

class EnneagramTestResultDaoImpl: EnneagramTestResultDao {
    override suspend fun getTestResultByUserId(userId: Int): EnneagramTestResultDto? {

        return ktormDatabase.sequenceOf(DBEnneagramTestResultsTable)
            .firstOrNull { it.userId eq userId }?.toDto()
    }


    override suspend fun addTestResult(testResult: EnneagramTestResultDto) {
        MySqlDatabaseConfig.ktormDatabase.insert(DBEnneagramTestResultsTable) {
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
            set(it.dominantTypeScore, testResult.dominantTypeScore)
            set(it.wingType, testResult.wingType)
            set(it.wingTypeScore, testResult.wingTypeScore)
        }
    }

    override suspend fun deleteTestResultByUserId(userId: Int) {
        ktormDatabase.delete(DBEnneagramTestResultsTable){
            it.userId eq userId
        }
    }
}
