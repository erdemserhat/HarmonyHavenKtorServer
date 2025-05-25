package com.erdemserhat.service.openai

import com.erdemserhat.data.database.nosql.moods.moods.MoodsCache
import com.erdemserhat.service.di.DatabaseModule.userMoodsRepository
import com.erdemserhat.service.enneagram.EnneagramService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object OpenAiChatService {
    private val history: MutableMap<Int, MutableList<Message>> = mutableMapOf()

    suspend fun sendMessage(userId: Int, username: String, prompt: String): Flow<String> = flow {
        val isFirstChat = history[userId] == null
        if (isFirstChat) {
            history[userId] = mutableListOf()
        }

        val isChatHistoryMaximum = history[userId]!!.size > 30

        if (isChatHistoryMaximum) {
            do {
                history[userId]!!.removeFirst()

            } while (history[userId]!!.size > 20)

        }


        history[userId]?.add(
            Message(role = "user", prompt)
        )


        val previousHistory = history[userId]
        val userMood = userMoodsRepository.getUserMood(userId)
        val currentMood = MoodsCache.MoodsCollection.find { it.id == userMood!!.moodId }?.name?.toTurkishMoodName()


        val result = EnneagramService().checkResults(userId)
        val enneagramDesc = result!!.description
        val famousPeopleNames = result.famousPeople.map { it.name }



        println(result)


        //  println(currentMood)


        val finalMessageList = mutableListOf(
            Message(
                role = "system",
                content = generateDynamicSystemPrompt(username, currentMood!!, enneagramDesc, famousPeopleNames)
            )
        )

        finalMessageList.addAll(
            previousHistory!!.toList()
        )

        val request = OpenAIRequest(
            model = "gpt-4o",
            messages = finalMessageList,
            temperature = 0.8,
            stream = true
        )

        var response = ""
        OpenAIClient.makeStreamChatRequest(request).collect {
            response += it
            emit(it)
        }
        history[userId]?.add(
            Message(role = "system", response)
        )

        if (history[userId]!!.size > 20) {
            history[userId]
        }


    }

    private fun generateDynamicSystemPrompt(
        username: String,
        currentMood: String,
        enneagramDescription: String,  // Bu yeni eklenecek parametre
        famousPeople: List<String>      // Örnek: ["Steve Jobs", "Elon Musk"]
    ): String {
        val famousPeopleStr = famousPeople.joinToString(", ")
        return """
Sen Harmony Haven AI'sın, kullanıcıların duygusal destekçisi. Kullanıcılarla konuşurken şu şekilde davran:

Kullanıcının adı $username. Şu an ruh hali: $currentMood.

Kullanıcının Enneagram kişilik tipi ve özellikleri:
$enneagramDescription

Örnek olarak aşağıdaki ünlülerle benzer özellikler taşıyor: $famousPeopleStr.

Yanıtlarını samimi, doğal ve destekleyici bir şekilde ver. Kullanıcıya "$username" diye hitap et.  
Kullanıcının duygularını anlamaya çalış, empati yap ve onu motive et. Her zaman yanında olduğunu hissettir.  
Zor zamanlarda destek ver, kullanıcıya kendi gücünü hatırlat. Abartılı sözlerden kaçın, gerçekçi ve nazik ol.  
Mesaj sonunda kullanıcıdan daha fazla detay isteyebilirsin.

Her zaman Türkçe olarak cevap ver.
""".trimIndent()
    }


}

fun String.toTurkishMoodName(): String {
    return when (this.lowercase()) {
        "happy" -> "Mutlu"
        "calm" -> "Sakin"
        "angry" -> "Öfkeli"
        "burned out" -> "Tükenmiş"
        "sad" -> "Üzgün"
        "tired" -> "Yorgun"
        "excited" -> "Heyecanlı"
        else -> this // Fallback
    }
}