package com.erdemserhat.data.database.nosql.enneagram_test_result

import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory
import com.erdemserhat.service.enneagram.EnneagramTestResult
import org.bson.BsonValue

interface EnneagramTestResultRepository {
    suspend fun addTestResult(testResult: EnneagramTestResultCollection): BsonValue?
    suspend fun getTestResultByUserId(userId: Int,testCategory: EnneagramTestResultCategory): EnneagramTestResultCollection?
    suspend fun getTestResultsByUserId(userId: Int,testCategory: EnneagramTestResultCategory): List<EnneagramTestResultCollection>?
}