package com.erdemserhat.service.openai

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object OpenAiChatService {
    private val history: MutableMap<Int, MutableList<Message>> = mutableMapOf()

    suspend fun sendMessage(userId: Int, username: String, prompt: String): Flow<String> = flow {
        val isFirstChat = history[userId] == null
        if (isFirstChat) {
            history[userId] = mutableListOf()
        }

        val isChatHistoryMaximum= history[userId]!!.size>30

        if (isChatHistoryMaximum) {
            do {
                history[userId]!!.removeFirst()

            }while (history[userId]!!.size>20)

        }


        history[userId]?.add(
            Message(role = "user",prompt)
        )



        val previousHistory = history[userId]



        val finalMessageList = mutableListOf(
            Message(role = "system", content = generateDynamicSystemPrompt(username))
        )

        finalMessageList.addAll(
            previousHistory!!.toList()
        )

        val request = OpenAIRequest(
            model = "gpt-3.5-turbo",
            messages = finalMessageList,
            temperature = 0.7,
            stream = true
        )

        var response = ""
        OpenAIClient.makeStreamChatRequest(prompt, username, request).collect{
            response+=it
            emit(it)
        }
        history[userId]?.add(
            Message(role = "system",response)
        )

        if(history[userId]!!.size>20) {
            history[userId]
        }


    }

    private fun generateDynamicSystemPrompt(username: String): String {

        val systemPrompt = """
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
                    - kullanıcıya daime "$username" diye hitap et.
                    - Kullanıcı üzgünse: "Bu durumda kendinizi üzgün hissetmeniz çok doğal 😔"
                    - Kullanıcı mutluysa: "Harika bir ilerleme kaydetmişsiniz! 🌟"
                    - Kullanıcı endişeliyse: "Bu endişelerinizi çok iyi anlıyorum 😟"
                    - Kullanıcı başarılıysa: "Başarınızı kutlamak istiyorum! 🎉"
                    - Kullanıcı yorgunsa: "Kendinizi yorgun hissetmeniz normal 😴"
                    - Cesaretlendirme gerektiğinde: "İçinizdeki gücü hatırlayın 💪"
                    
                    Her zaman Türkçe olarak cevap ver.
                """.trimIndent()
        return systemPrompt

    }


}