package com.erdemserhat.dto.requests

import kotlinx.serialization.Serializable


@Serializable
data class OpenAIPromptDto(
    val prompt:String
)

@Serializable
data class OpenAIPromptRespondDto(
    val prompt:String
)
