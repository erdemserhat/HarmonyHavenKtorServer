package com.erdemserhat.service.openai

import io.ktor.server.application.*

fun Application.configureOpenAiCredentials() {
    // Read FTP configuration from environment properties
    val key = environment.config.property("openai.key").getString()
    // Initialize FTP configuration with the FTP model
    OpenAICredentials.init(key)
}

object OpenAICredentials{
    var API_KEY:String = ""
    private set
    val HOST = "api.openai.com"
    val PORT = 443
    val PATH = "/v1/chat/completions"
    val SCHEME = "https"

    fun init(key:String){
        if(API_KEY ==""){
            API_KEY = key
        }


    }
}
