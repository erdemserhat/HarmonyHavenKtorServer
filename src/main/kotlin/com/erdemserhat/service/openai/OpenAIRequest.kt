package com.erdemserhat.service.openai

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.Chat
import com.erdemserhat.service.di.OpenAIModule
import kotlinx.coroutines.*

class OpenAIRequest(
    private val prompt: String,
    private val modelId: String = "gpt-3.5-turbo",
    private val chatRole: ChatRole = ChatRole.User,

    ) {

    suspend fun getResult(): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = """
                Kullanıcı senden sadece doğrudan istenileni yazmanı bekliyor. Cevapların başlangıç cümleleri olarak 'Elbette!', 'Tabii ki!' veya benzeri ifadeleri kullanma. Sadece verilen talimatlara uygun bir yanıt ver.
                """.trimIndent()
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Cevabının tamamında sadece istenileni yaz, başka herhangi bir ek açıklama veya görüş olmasın."
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Cevabın kullanıcıya Ali diyerek hitap etmelisin!"
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Cevabının tamamında, şahsi hiçbir cevap verme sadece istenileni yaz."
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Kullanıcı senden bir arkadaşı için değil kendisi için cevap vermeni bekliyor."
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Vereceğin cevabın tamamında  ek açıklamalar, örn: 'İşte istediğiniz mesaj', 'Tabii ki!, işte mesajınız','Elbette!' gibi ifadeler bulunmasın."
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Doğrudan istenileni yaz."
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Emojiler kullanabilirsin."
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Türkçe olarak cevap ver."
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Tırnak işareti kullanma, tırnak işareti içermesin cevabın."
                ),



                ChatMessage(
                    role = ChatRole.User,
                    content = prompt,
                )
            )
        )

        val completion = OpenAIModule.openAI.chatCompletion(chatCompletionRequest)

        return completion.choices[0].message.content!!
    }


}