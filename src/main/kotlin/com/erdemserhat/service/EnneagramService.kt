package com.erdemserhat.service

import com.erdemserhat.data.database.enneagram.enneagram_answers.EnneagramAnswersDto
import com.erdemserhat.data.database.enneagram.enneagram_questions.EnneagramQuestionDto
import com.erdemserhat.data.database.enneagram.enneagram_test_results.EnneagramTestResultDto
import com.erdemserhat.service.di.EnneagramRepositoryModule
import com.erdemserhat.service.notations.Service
import kotlinx.coroutines.*


@Service
class EnneagramService(
    private val enneagramRepository: EnneagramRepositoryModule = EnneagramRepositoryModule,
) {
    suspend fun getQuestions(): List<EnneagramQuestionDto> {
        return enneagramRepository.enneagramQuestionRepository.getQuestions()
    }


    suspend fun evaluateTestResult(answers: List<EnneagramAnswersDto>): EnneagramTestResultDto {
        val questions = enneagramRepository.enneagramQuestionRepository.getQuestions()
        val typeScores = MutableList(9) { 0 }

        answers.forEach { answer ->
            val score = answer.score
            val type = questions.find { answer.questionId == it.id }!!.personalityNumber
            typeScores[type - 1] += score


        }
        val dominantTypeScore = typeScores.maxOrNull() ?: 0

        val result = EnneagramTestResultDto(
            typeOneScore = typeScores[0],
            typeTwoScore = typeScores[1],
            typeThreeScore = typeScores[2],
            typeFourScore = typeScores[3],
            typeFiveScore = typeScores[4],
            typeSixScore = typeScores[5],
            typeSevenScore = typeScores[6],
            typeEightScore = typeScores[7],
            typeNineScore = typeScores[8],
            dominantType = typeScores.indexOf(dominantTypeScore) + 1,

            )

        return result


    }

    suspend fun saveTestResult(
        testResultDto: EnneagramTestResultDto,
        answers: List<EnneagramAnswersDto>,
        userId: Int
    ) =
        withContext(Dispatchers.IO) {
            enneagramRepository.enneagramAnswersRepository.deleteAnswersByUserId(userId)
            enneagramRepository.enneagramAnswersRepository.addAnswers(answers, userId)
            enneagramRepository.enneagramTestResultsRepository.addTestResult(testResultDto)



        }


}
