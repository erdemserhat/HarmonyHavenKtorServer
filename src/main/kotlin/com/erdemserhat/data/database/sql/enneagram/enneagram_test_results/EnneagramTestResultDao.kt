package com.erdemserhat.data.database.sql.enneagram.enneagram_test_results

interface EnneagramTestResultDao {
    suspend fun getTestResultByUserId(userId:Int):EnneagramTestResultDto?
    suspend fun addTestResult(testResult: EnneagramTestResultDto)
    suspend fun deleteTestResultByUserId(userId: Int)
}