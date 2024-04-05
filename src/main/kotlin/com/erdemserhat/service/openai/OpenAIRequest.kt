package com.erdemserhat.service.openai

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.erdemserhat.service.di.OpenAIModule
import kotlinx.coroutines.*

class OpenAIRequest(
    private val prompt: String,
    private val modelId: String = "gpt-3.5-turbo",
    private val chatRole: ChatRole = ChatRole.System,

    ) {

    suspend fun getResult(): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId),
            messages = listOf(
                ChatMessage(
                    role = chatRole,
                    content = prompt
                )
            )
        )

        val completion = OpenAIModule.openAI.chatCompletion(chatCompletionRequest)
        return completion.choices[0].message.content!!
    }
}