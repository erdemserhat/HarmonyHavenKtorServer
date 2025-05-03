package com.erdemserhat.service.enneagram

import com.erdemserhat.data.database.nosql.enneagram_chart.toEnneagramUrl
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramFamousPeopleCollection
import com.erdemserhat.data.database.nosql.enneagram_famous_people.EnneagramType
import com.erdemserhat.data.database.nosql.enneagram_famous_people.toDto
import com.erdemserhat.data.database.nosql.enneagram_question.EnneagramQuestionCategory
import com.erdemserhat.data.database.nosql.enneagram_question.toDto
import com.erdemserhat.data.database.nosql.enneagram_test_result.EnneagramTestResultCategory
import com.erdemserhat.data.database.nosql.enneagram_test_result.EnneagramTestResultCollection
import com.erdemserhat.data.database.nosql.enneagram_test_result.EnneagramWingTypes
import com.erdemserhat.data.database.nosql.enneagram_test_result.toEnneagramTestResult
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.EnneagramTypeDescriptionCategory
import com.erdemserhat.data.database.nosql.enneagram_type_descriptions.toEnneagramExtraTypeDescriptionCategory
import com.erdemserhat.data.database.sql.enneagram.enneagram_answers.EnneagramAnswerDto
import com.erdemserhat.data.database.sql.enneagram.enneagram_questions.EnneagramQuestionDto
import com.erdemserhat.data.database.sql.enneagram.enneagram_test_results.EnneagramTestResultDto
import com.erdemserhat.service.di.EnneagramRepositoryModule
import com.erdemserhat.service.notations.Service
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Service
class EnneagramService(
    private val enneagramRepository: EnneagramRepositoryModule = EnneagramRepositoryModule,
) {
    suspend fun getQuestions(questionCategory: EnneagramQuestionCategory): List<EnneagramQuestionDto> {
        return when (questionCategory) {
            EnneagramQuestionCategory.BASIC -> {
                enneagramRepository.enneagramQuestionRepository.findByQuestionCategory(EnneagramQuestionCategory.BASIC)
                    ?.map {
                        it.toDto()
                    } ?: emptyList()
            }

            EnneagramQuestionCategory.STANDARD -> {
                enneagramRepository.enneagramQuestionRepository.findByQuestionCategory(EnneagramQuestionCategory.STANDARD)
                    ?.map {
                        it.toDto()
                    } ?: emptyList()
            }

            EnneagramQuestionCategory.PROFESSIONAL -> {
                enneagramRepository.enneagramQuestionRepository.findByQuestionCategory(EnneagramQuestionCategory.PROFESSIONAL)
                    ?.map {
                        it.toDto()
                    } ?: emptyList()
            }

        }
    }

    private suspend fun getEnneagramTypeDescription(enneagramType: EnneagramType, pointBasedWingType:Int, descriptionCategory: EnneagramTypeDescriptionCategory):String{
        val description = enneagramRepository.enneagramTypeDescriptionRepository.getDescriptionByTypeAndCategory(
            category = descriptionCategory,
            type = enneagramType
        )
        val ifExistExtraDescription = enneagramType.wingType != pointBasedWingType

        if(ifExistExtraDescription){
            val extraDescription = enneagramRepository.enneagramExtraTypeDescriptionRepository.getExtraTypeDescriptionByTypeAndCategory(
                type = pointBasedWingType,
                category = descriptionCategory.toEnneagramExtraTypeDescriptionCategory()
            )

            return extraDescription?.description ?: "no description found"

        }else{
            return description?.description ?: "no description found"
        }

    }


    suspend fun evaluateTestResult(
        mode: EvaluationMode,
        answers: List<EnneagramAnswerDto>,
        userId:Int
    ): EnneagramTestResultDetailedDto {



        val typeScores: MutableList<EnneagramScore> = mutableListOf(
            EnneagramScore(1, 0),
            EnneagramScore(2, 0),
            EnneagramScore(3, 0),
            EnneagramScore(4, 0),
            EnneagramScore(5, 0),
            EnneagramScore(6, 0),
            EnneagramScore(7, 0),
            EnneagramScore(8, 0),
            EnneagramScore(9, 0),
        )


        val questions = when (mode) {
            EvaluationMode.BASIC -> getQuestions(EnneagramQuestionCategory.BASIC)  // 36 Questions (4 Question per type)
            EvaluationMode.STANDARD -> getQuestions(EnneagramQuestionCategory.STANDARD) // 72 Questions (8 Question per type)
            EvaluationMode.PROFESSIONAL -> getQuestions(EnneagramQuestionCategory.PROFESSIONAL)
        }

        //calculate score for each type
        answers.forEach { answer ->
            val score = answer.score
            val type = questions.find { answer.questionId == it.id }!!.personalityNumber
            typeScores.find { it.type == type }!!.score += score

        }
        val pointList = listOf(0, 1, 2, 3)
        val answersSize = answers.size
        val maxScorePerType = (answersSize / 9) * pointList.max()


        //selects dominant type
        val dominantTypeScore = typeScores.maxByOrNull { it.score }?.score ?: 0
        val dominantData = typeScores.find { it.score == dominantTypeScore }!!
        val dominantType = typeScores.find { it.score == dominantTypeScore }?.type!!


        //selects wing type (second dominant type)
        var wingTypeScore = EnneagramScore(-1, -1)
        for (scoreData in typeScores) {

            if (scoreData != dominantData && wingTypeScore.score < scoreData.score) {
                wingTypeScore = scoreData
            }
        }
        val wingType = wingTypeScore

        val dominantTypeConditionPoint = (maxScorePerType * 0.7).toInt() // %70

        val checkDominantStatus: (dominantTypeConditionPoint: Int, dominantTypePoint: Int) -> Boolean = { a, b ->
            b >= a
        }

        val isStronglyDominant = checkDominantStatus(dominantTypeConditionPoint, dominantTypeScore)


        var description = ""


        val validWings = when (dominantData.type) {
            1 -> listOf(2, 9)
            2 -> listOf(1, 3)
            3 -> listOf(2, 4)
            4 -> listOf(3, 5)
            5 -> listOf(4, 6)
            6 -> listOf(5, 7)
            7 -> listOf(6, 8)
            8 -> listOf(7, 9)
            9 -> listOf(1, 8)
            else -> throw IllegalArgumentException("Invalid dominantType: ${dominantData.type}.dominantType")
        }


        val defineEnneagramBasedDominantWingType: (validWings: List<Int>) -> EnneagramScore =
            { validWingsLocal ->
                val firstWing = validWingsLocal[0]
                val secondWing = validWingsLocal[1]


                val actualWingPoint = listOf(
                    typeScores.find { it.type == firstWing },
                    typeScores.find { it.type == secondWing }).maxByOrNull { it!!.score }!!

                actualWingPoint

            }


        val enneagramBasedDominantWingType = defineEnneagramBasedDominantWingType(validWings)

        val pointBasedDominantWingType = wingType.type

        val enneagramType = EnneagramType(dominantData.type,enneagramBasedDominantWingType.type)
        val generalType = "${dominantData.type}" + "w" + "${enneagramBasedDominantWingType.type}"



        if (mode == EvaluationMode.BASIC) {
            if (isStronglyDominant) {
                println("Strongly Dominant")
                description = getEnneagramTypeDescription(
                    enneagramType = enneagramType,
                    pointBasedWingType = pointBasedDominantWingType,
                    descriptionCategory = mode.toEnneagramTypeDescriptionCategory()
                )


            } else {
                //use ai results...
                println("Not Strongly Dominant")
                description = getEnneagramTypeDescription(
                    enneagramType = enneagramType,
                    pointBasedWingType = pointBasedDominantWingType,
                    descriptionCategory = mode.toEnneagramTypeDescriptionCategory()
                )

            }
        } else if (mode == EvaluationMode.STANDARD) {
            if (isStronglyDominant) {
                //static results...

            } else {
                //use ai results...
            }

        }


        val result = EnneagramTestResult(
            typeScores = typeScores,
            dominantType = dominantData,
            wingType = EnneagramWingTypes(
                pointBasedWingType = pointBasedDominantWingType,
                enneagramBasedWingType = enneagramBasedDominantWingType.type


            ),
        )

        val famousPeople = enneagramRepository.enneagramFamousPeopleRepository.findByEnneagramType(
            enneagramType
        )?.map { it.toDto() } ?: emptyList()

        val chart = enneagramRepository.enneagramChartRepository.getChartByType(enneagramType)

        val detailedResult = EnneagramTestResultDetailedDto(
            result = result,
            description = description,
            famousPeople = famousPeople,
            chartUrl = chart?.toEnneagramUrl() ?: null,
        )

        val resultCollection = EnneagramTestResultCollection(
            userId = userId,
            typeScores = typeScores,
            answers = answers,
            dominantType = EnneagramScore(dominantData.type,dominantData.score),
            wingTypes = EnneagramWingTypes(
                pointBasedWingType = pointBasedDominantWingType,
                enneagramBasedWingType = enneagramBasedDominantWingType.type

            ),
            testCategory = EnneagramTestResultCategory.BASIC,
            createdAt = LocalDateTime.now(),

        )

        enneagramRepository.enneagramTestResultRepository.addTestResult(
            resultCollection
        )


        return detailedResult


    }

    suspend fun saveTestResult(
        testResultDto: EnneagramTestResultDto, answers: List<EnneagramAnswerDto>, userId: Int
    ) = withContext(Dispatchers.IO) {


    }

    suspend fun checkResults(userId:Int):EnneagramTestResultDetailedDto?{
        val testResult = enneagramRepository.enneagramTestResultRepository.getTestResultByUserId(
            userId = userId,
            testCategory = EnneagramTestResultCategory.BASIC
        )
        if (testResult == null) return null

        val enneagramType = EnneagramType(
            dominantType = testResult.dominantType.type,
            wingType = testResult.wingTypes.enneagramBasedWingType
        )

        val typeDescription = getEnneagramTypeDescription(
            enneagramType = enneagramType,
            pointBasedWingType = testResult.wingTypes.pointBasedWingType,
            descriptionCategory = EnneagramTypeDescriptionCategory.BASIC
        )

        val chart = enneagramRepository.enneagramChartRepository.getChartByType(
            enneagramType = enneagramType,
        )

        val famousPeople = enneagramRepository.enneagramFamousPeopleRepository.findByEnneagramType(
            enneagramType = enneagramType,
        )?.map { it.toDto() } ?: emptyList()

        val detailedResult = EnneagramTestResultDetailedDto(
            result = testResult.toEnneagramTestResult(),
            description = typeDescription,
            famousPeople = famousPeople,
            chartUrl = chart?.toEnneagramUrl(),
        )

        return detailedResult







    }


}


@Serializable
data class EnneagramTestResultDetailedDto(
    val result: EnneagramTestResult,
    val description: String,
    val famousPeople: List<EnneagramFamousPeopleDto>,
    val chartUrl: EnneagramUrl?
)

@Serializable
data class EnneagramUrl(
    val chartUrl:String,
    val personalityImageUrl:String,

)


@Serializable
data class EnneagramFamousPeopleDto(
    val name: String,
    val imageUrl: String,
    val desc: String
)

fun EnneagramFamousPeopleDto.toCollection(
    enneagramType: EnneagramType
):EnneagramFamousPeopleCollection{
    return EnneagramFamousPeopleCollection(
        name = this.name,
        imageUrl = imageUrl,
        desc = desc,
        enneagramType = enneagramType
    )
}


@Serializable
data class EnneagramScore(
    val type: Int = 0,
    var score: Int = 1
)






@Serializable
data class EnneagramTestResult(
    val typeScores: List<EnneagramScore>,
    val dominantType: EnneagramScore,
    val wingType: EnneagramWingTypes,
)




@Serializable
data class CheckingTestResultDto(
    val detailedResult: EnneagramTestResultDetailedDto?,
    val isTestTakenBefore: Boolean
)