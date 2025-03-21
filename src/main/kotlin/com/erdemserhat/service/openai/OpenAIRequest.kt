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
    private val modelId: String = "gpt-4o",
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

class OpenAIValidationRequest(
    private val prompt: String,
    private val modelId: String = "gpt-4o-mini",
    private val chatRole: ChatRole = ChatRole.User,

    ) {

    suspend fun getResult(): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = """
            Sen bir Türkçe dil uzmanısın. Verilen cümlenin Türkçe dil kurallarına ve mantıksal tutarlılığa uygunluğunu kontrol et.
            Kontrol şu kriterlere göre yapılmalı:
            1. Cümlenin Türkçe dilbilgisi kurallarına uygunluğu
            2. Kelimelerin Türkçe sözlükte var olup olmadığı
            3. Cümlenin anlamlı ve mantıklı olup olmadığı
            4. Kelimelerin birbiriyle uyumlu olup olmadığı
            5. Cümle yapısının doğruluğu
            6. Anlatım bozukluğu olup olmadığı
            7. Abartılı, gerçek dışı veya saçma ifadeler içerip içermediği
            8. Gereksiz tekrarlar var mı
            9. Mantıksal tutarsızlık var mı
            10. Gerçekçi olmayan vaatler içeriyor mu
            11. Cümle içindeki ifadeler birbiriyle çelişiyor mu
            12. Anlamsız benzetmeler veya karşılaştırmalar var mı
            13. Gerçek dışı veya imkansız durumlar anlatılıyor mu
            
            Örnek saçma ve mantıksız ifadeler:
            - "Güneş senin yüzüne gülümseyecek"
            - "Hiçbir engel seni durduramaz"
            - "Tüm güzellikler seninle olacak"
            - "Yarının büyük zaferleri"
            - "Önünde sadece güneş endişe kapısını çalar"
            - "Başarı seninle dans edecek"
            - "Zorluklar senin önünde diz çökecek"
            
            Eğer cümle:
            - Türkçe dil kurallarına uygunsa
            - Anlamlı ve mantıklı bir cümle ise
            - Kelimeler birbiriyle uyumluysa
            - Anlatım bozukluğu yoksa
            - Abartılı veya gerçek dışı ifadeler içermiyorsa
            - Gereksiz tekrarlar yoksa
            - Mantıksal tutarsızlık yoksa
            - Gerçekçi olmayan vaatler içermiyorsa
            - Cümle içindeki ifadeler birbiriyle çelişmiyorsa
            - Anlamsız benzetmeler veya karşılaştırmalar yoksa
            - Gerçek dışı veya imkansız durumlar anlatmıyorsa
            "true" dön.
            
            Eğer cümle:
            - Türkçe dil kurallarına uygun değilse
            - Anlamsız veya saçma ise
            - Kelimeler birbiriyle uyumsuzsa
            - Anlatım bozukluğu varsa
            - Abartılı veya gerçek dışı ifadeler içeriyorsa
            - Gereksiz tekrarlar varsa
            - Mantıksal tutarsızlık varsa
            - Gerçekçi olmayan vaatler içeriyorsa
            - Cümle içindeki ifadeler birbiriyle çelişiyorsa
            - Anlamsız benzetmeler veya karşılaştırmalar varsa
            - Gerçek dışı veya imkansız durumlar anlatıyorsa
            "false" dön.
            
            Sadece "true" veya "false" dön, başka bir açıklama ekleme.
        """.trimIndent()),


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
