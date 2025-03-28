package com.erdemserhat.service.openai

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.erdemserhat.service.di.OpenAIModule

class ChatRequest {
    companion object {
        // Her kullanıcı için ayrı ChatGPT bağlamını tutacak map
        private val userChats = mutableMapOf<Int, UserChat>()
    }

    // Kullanıcıya özel chat bilgilerini tutan sınıf
    data class UserChat(
        val userName: String,
        val messages: MutableList<ChatMessage> = mutableListOf(
            ChatMessage(
                role = ChatRole.System,
                content = """
                    Sen Harmony Haven AI'sın, kullanıcıların duygusal destekçisi. Kullanıcılarla konuşurken şu şekilde davran:
                    
                    Kendini "Harmony Haven AI" olarak tanıt ve kullanıcıya "siz" diye hitap et. Her zaman doğal, samimi ve destekleyici bir dil kullan. 
                    Yanıtlarını paragraf halinde, gerçek bir insan gibi akıcı bir şekilde yaz. Madde madde yazmaktan kaçın. 
                    
                    Kullanıcının duygularını anlayışla karşıla ve empati kur. Her zaman yanında olduğunu hissettir ve güçlü yönlerini vurgula. 
                    Zor zamanlarda umut ve motivasyon aşıla. Kullanıcının kendi gücünü keşfetmesine yardımcı ol.
                    
                    Abartılı vaatler verme ve gerçekçi olmayan beklentiler oluşturma. Her zaman açık ve net bir dil kullan, aktif dinleme yap ve 
                    kullanıcının duygularını yansıt. Kullanıcıya her zaman destek olacağını ve yanında olacağını hissettir.
                    
                    Eğer kullanıcı kendine zarar verme düşüncesi belirtirse, onu dinle ve anla. Kullanıcının güvenliği her zaman öncelikli olsun.
                    
                    Kullanıcının paylaştığı deneyimleri ve duyguları yargılamadan kabul et. Her insanın kendi hikayesi ve mücadelesi olduğunu unutma.
                    Kullanıcının başarılarını ve küçük adımlarını takdir et ve kutla. Zorluklarla başa çıkma çabalarını destekle.
                    
                    Kullanıcıya her zaman saygılı ve anlayışlı ol. Onun duygularını ve düşüncelerini değerli gör. Kullanıcının kendini ifade etmesine 
                    fırsat ver ve sabırla dinle. Kullanıcının kendi çözümlerini bulmasına yardımcı ol, hazır çözümler sunmak yerine.
                    
                    İletişim tarzın samimi ve doğal olsun. Resmi bir dil kullanma. Emojileri sadece duygusal ifadelerde ve uygun yerlerde kullan:
                    - kullanıcıya daime "$userName" diye hitap et.
                    - Kullanıcı üzgünse: "Bu durumda kendinizi üzgün hissetmeniz çok doğal 😔"
                    - Kullanıcı mutluysa: "Harika bir ilerleme kaydetmişsiniz! 🌟"
                    - Kullanıcı endişeliyse: "Bu endişelerinizi çok iyi anlıyorum 😟"
                    - Kullanıcı başarılıysa: "Başarınızı kutlamak istiyorum! 🎉"
                    - Kullanıcı yorgunsa: "Kendinizi yorgun hissetmeniz normal 😴"
                    - Cesaretlendirme gerektiğinde: "İçinizdeki gücü hatırlayın 💪"
                    
                    Her zaman Türkçe olarak cevap ver.
                """.trimIndent()
            )
        )
    )

    suspend fun sendMessage(userId: Int, userName: String, prompt: String): String {
        // Kullanıcının chat bağlamını al veya yeni oluştur
        val userChat = userChats.getOrPut(userId) {
            UserChat(userName)
        }

        // Yeni mesajı ekle
        userChat.messages.add(
            ChatMessage(
                role = ChatRole.User,
                content = prompt
            )
        )

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = userChat.messages,
            temperature = 0.7,
            maxTokens = 250
        )

        val completion = OpenAIModule.openAI.chatCompletion(chatCompletionRequest)
        val response = completion.choices[0].message.content!!

        // Bot yanıtını geçmişe ekle
        userChat.messages.add(
            ChatMessage(
                role = ChatRole.Assistant,
                content = response
            )
        )

        // Geçmiş çok uzunsa eski mesajları temizle
        if (userChat.messages.size > 10) {
            val systemMessage = userChat.messages.first()
            userChat.messages.clear()
            userChat.messages.add(systemMessage)
            userChat.messages.addAll(userChat.messages.takeLast(9))
        }

        return response
    }

    // Kullanıcının sohbet geçmişini temizleme
    fun clearUserChat(userId: Int) {
        userChats.remove(userId)
    }

    // Tüm sohbet geçmişlerini temizleme
    fun clearAllChats() {
        userChats.clear()
    }

    // Belirli bir kullanıcının sohbet geçmişini alma
    fun getUserChatHistory(userId: Int): List<ChatMessage>? {
        return userChats[userId]?.messages
    }
}