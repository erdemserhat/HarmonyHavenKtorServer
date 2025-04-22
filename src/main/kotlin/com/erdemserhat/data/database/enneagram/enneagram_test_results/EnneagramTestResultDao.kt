package com.erdemserhat.data.database.enneagram.enneagram_test_results

interface EnneagramTestResultDao {
    suspend fun getTestResultByUserId(userId:Int):EnneagramTestResultsDto
    suspend fun addTestResult(testResult: EnneagramTestResultsDto)
}