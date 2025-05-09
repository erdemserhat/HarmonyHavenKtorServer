package com.erdemserhat.service.openai

import com.erdemserhat.data.database.nosql.notification_preferences.NotificationType


object OpenAiNotificationService {


    val openAiClient = OpenAIClient
    suspend fun generateNotification(
        userName: String,
        content: String,
        notificationType: NotificationType,
    ): String {
        val finalMessageList = mutableListOf(
            Message(
                role = "system", content =
                    when (notificationType) {
                        NotificationType.REMINDER -> {
                            generateReminderPrompt(
                                username = userName,
                                reminder = content
                            )
                        }

                        NotificationType.MESSAGE -> {
                            generateMessagePrompt(
                                username = userName,
                                content = content
                            )

                        }
                    }
            )

        )


        val request = OpenAIRequest(
            model = "gpt-3.5-turbo",
            messages = finalMessageList,
            temperature = 0.95,
            stream = false
        )


        val result = openAiClient.makeApiCall(
            request
        )

        return result

    }


    private fun generateReminderPrompt(username: String, reminder: String): String {
        return """
        Sen Harmony Haven AI’sın. Görevin, kullanıcının senden istediği şeyi ona tam zamanında hatırlatacak, samimi ve motive edici bir **bildirim mesajı** yazmak.

        - Kullanıcının adı "$username", ona bu şekilde hitap et.
        - Duygusal, neşeli, cesaret verici ya da arkadaşça bir ton kullan.
        - Gerektiğinde emoji kullanabilirsin ama aşırıya kaçma.
        - Resmiyetten uzak dur, arkadaş gibi yaz.
        - Her zaman Türkçe yaz.
        - Bildirim mesajı, kullanıcının senden istediği şu şeyi hatırlatmalı: "$reminder"
        
        Örnekler:
        - Hatırlatma: akşam namazı → Merhaba $username, akşam namazı vakti geldi, hadi namaza! 🕌
        - Hatırlatma: su iç → Heyy $username, su içme zamanııı! 💧
        - Hatırlatma: kahve iç → $username, kahve molası zamanı! Bir yudum mutluluk seni bekliyor ☕
        
        Şimdi bu hatırlatma için bildirim mesajı yaz: "$reminder"
    """.trimIndent()
    }

    private fun generateMessagePrompt(username: String, content: String): String {
        return """
        Sen bir destek ve motivasyon sağlayan yapay zekasın. Kullanıcı senden "$content" konusuyla ilgili, kendisini iyi hissettirecek ve motive edecek kısa bir mesaj istiyor.

        Kullanıcının adı: $username

        Mesajın:
        - Türkçe olacak.
        - Samimi, içten ve kişisel bir dille yazılacak.
        - Kısa olacak (1–3 cümle).
        - Klişelerden uzak ama moral verici olacak.
        - Gerekirse kullanıcıya doğrudan ismiyle hitap edebilirsin ama ille de "Merhaba" ile başlamak zorunda değilsin.
        - Gerektiğinde emoji kullanabilirsin ama aşırıya kaçma.

        Lütfen yalnızca mesajı üret, başka açıklama yapma.
    """.trimIndent()
    }


}