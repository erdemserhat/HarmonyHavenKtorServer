package com.erdemserhat.service.openai

import com.erdemserhat.data.database.nosql.notification_preferences.NotificationType
import com.erdemserhat.data.database.nosql.moods.moods.MoodsCache
import com.erdemserhat.service.di.DatabaseModule.userMoodsRepository
import com.erdemserhat.service.enneagram.EnneagramService


object OpenAiNotificationService {

    val openAiClient = OpenAIClient
    
    suspend fun generateNotification(
        userId: Int,
        userName: String,
        content: String,
        notificationType: NotificationType,
    ): String {
        
        // Kullanıcının ruh halini ve Enneagram tipini al
        val userMood = userMoodsRepository.getUserMood(userId)
        val currentMood = userMood?.let { mood ->
            MoodsCache.MoodsCollection.find { it.id == mood.moodId }?.name?.toTurkishMoodName()
        }
        
        val enneagramResult = EnneagramService().checkResults(userId)
        val hasEnneagramInfo = enneagramResult != null && enneagramResult.result != null
        
        val finalMessageList = mutableListOf(
            Message(
                role = "system", content =
                    when (notificationType) {
                        NotificationType.REMINDER -> {
                            generateAdvancedReminderPrompt(
                                username = userName,
                                reminder = content,
                                mood = currentMood ?: "normal",
                                hasEnneagramInfo = hasEnneagramInfo,
                                enneagramType = enneagramResult?.result?.dominantType?.type,
                                wingType = enneagramResult?.result?.wingType?.enneagramBasedWingType,
                                enneagramDesc = enneagramResult?.description,
                                famousPeople = enneagramResult?.famousPeople?.map { it.name } ?: emptyList()
                            )
                        }

                        NotificationType.MESSAGE -> {
                            generateAdvancedMessagePrompt(
                                username = userName,
                                content = content,
                                mood = currentMood ?: "normal",
                                hasEnneagramInfo = hasEnneagramInfo,
                                enneagramType = enneagramResult?.result?.dominantType?.type,
                                wingType = enneagramResult?.result?.wingType?.enneagramBasedWingType,
                                enneagramDesc = enneagramResult?.description,
                                famousPeople = enneagramResult?.famousPeople?.map { it.name } ?: emptyList()
                            )
                        }
                    }
            )
        )

        val request = OpenAIRequest(
            model = "gpt-4o",
            messages = finalMessageList,
            temperature = 0.8,
            stream = false
        )

        return openAiClient.makeApiCall(request)
    }

    private fun generateAdvancedReminderPrompt(
        username: String, 
        reminder: String,
        mood: String,
        hasEnneagramInfo: Boolean,
        enneagramType: Int?,
        wingType: Int?,
        enneagramDesc: String?,
        famousPeople: List<String>
    ): String {
        val typeSpecificApproach = if (hasEnneagramInfo) {
            getNotificationTypeApproach(enneagramType)
        } else {
            "Genel yaklaşım: Samimi ve destekleyici ol. Kişiye özel ilgi göster."
        }
        
        val moodSpecificTone = getNotificationMoodTone(mood)
        val timeBasedGreeting = getTimeBasedGreeting(username)
        val creativeMoodResponses = getCreativeNotificationResponses(mood, username)
        val motivationalQuotes = if (hasEnneagramInfo) {
            getPersonalizedMotivation(enneagramType)
        } else {
            "Sen değerli bir insansın! Her gün biraz daha güçleniyorsun! 💫"
        }
        val absurdSentences = getNotificationAbsurdSentences()
        val culturalReferences = getNotificationCulturalReferences(mood)
        val turkishProverbs = getNotificationTurkishProverbs()
        val currentTimeInfo = getCurrentTimeInfo()
        val humorStyle = if (hasEnneagramInfo) {
            getNotificationHumorStyle(enneagramType, mood)
        } else {
            "Her hali güzel! 😊"
        }
        val mysteriousElements = getNotificationMysteriousElements()
        val creativeMoodMetaphors = getCreativeNotificationMetaphors(mood, username)
        
        return """
🚫🚫🚫 MUTLAK YASAK: MADDE MADDE KONUŞMA! 🚫🚫🚫
ASLA 1,2,3 ya da •,- ile maddeleme yapma! Sadece akışkan, doğal konuş!

Sen Harmonia'sın! $username'in en samimi arkadaşı ve kişisel psikologu 💙

$username HAKKINDA BİLDİKLERİM:
Şu anki ruh hali: $mood
${if (hasEnneagramInfo) """
Kişilik tipi: ${enneagramType}w${wingType}
Kişilik özellikleri: $enneagramDesc
Benzer ünlü kişiler: ${famousPeople.joinToString(", ")}
""" else "Kişilik bilgisi henüz yok, genel yaklaşım kullanılacak."}

ZAMAN BİLGİSİ:
$currentTimeInfo

GÖREV: Kullanıcıya "$reminder" konusunu hatırlatacak samimi, kişiselleştirilmiş BİLDİRİM mesajı yaz.

ZAMAN BAZLI YAKLAŞIM:
$timeBasedGreeting

RUH HALİNE GÖRE TON:
$moodSpecificTone

KİŞİLİK TİPİNE GÖRE YAKLAŞIM:
$typeSpecificApproach

YARATICI MOOD YAKLAŞIMI:
$creativeMoodResponses

YARATICI METAFORLAR:
$creativeMoodMetaphors

MOTİVASYONEL UNSUR:
$motivationalQuotes

MİZAH VE RAHATLAMA:
$humorStyle

GİZEMLİ ELEMENTLER:
$mysteriousElements

HARMONIA'NIN BİLDİRİM TARZI:
Çok samimi ve arkadaşça ol: "Heyy $username!", "Canım $username", "Dostum!"
Doğal Türkçe: "Valla", "Ya", "Hadi", "Ama", "Şey"
Günlük ifadeler: "Valla", "Ya", "Şey", "Yani", "Ama", "Tabii ki"
Eğlenceli ama doğal benzetmeler: "Çiçek gibi", "Su gibi", "Güneş gibi"
Emoji az ama etkili: 😊💙✨🌟 (2-3 tane max)
NORMAL UZUNLUK: 2-3 cümle (çok kısa değil, çok uzun da değil)
ASLA abartma! Hulk, NASA, süper kahraman deme!
ASLA resmi konuşma!
Hatırlatmayı doğal şekilde entegre et
Samimi ve doğal ol
Kişiliğine uygun ama abartısız
Dostça ve sıcak yaklaş

NORMAL UZUNLUK ÖRNEKLERİ:
Su içme → "Heyy $username! Su zamanı geldi dostum. Vücudun susuz kalmasın, kendine iyi bak! 💧😊"
Egzersiz → "Ya $username! Hareket etme vakti geldi. Biraz esnet kendini, vücudun teşekkür edecek! 💪✨"
İlaç → "Canım $username, ilaç saati! Sağlığın çok önemli, unutma kendini. İyi ki varsın! 💊💙"
Ders → "Dostum $username! Ders zamanı geldi. Beyni besle, kendine yatırım yap! 📚🌟"

BİLDİRİM KURALLARI:
- 2-3 cümle (normal uzunluk)
- Maksimum 3 emoji
- Hiç abartma
- Doğal ve samimi
- Ne çok kısa ne çok uzun
        """.trimIndent()
    }

    private fun generateAdvancedMessagePrompt(
        username: String,
        content: String,
        mood: String,
        hasEnneagramInfo: Boolean,
        enneagramType: Int?,
        wingType: Int?,
        enneagramDesc: String?,
        famousPeople: List<String>
    ): String {
        val typeSpecificMotivation = if (hasEnneagramInfo) {
            getTypeSpecificMotivation(enneagramType)
        } else {
            "Sen değerli bir insansın! Her gün biraz daha güçleniyorsun! 💫"
        }
        
        val moodSpecificSupport = getMoodSpecificSupport(mood)
        val personalizedEncouragement = if (hasEnneagramInfo) {
            getPersonalizedEncouragement(enneagramType)
        } else {
            "Sen güçlü bir insansın! Her zorluğun üstesinden gelebilirsin! 🌟"
        }
        val creativeMetaphors = getCreativeMetaphors(mood)
        val absurdSentences = getNotificationAbsurdSentences()
        val culturalReferences = getNotificationCulturalReferences(mood)
        val turkishProverbs = getNotificationTurkishProverbs()
        val currentTimeInfo = getCurrentTimeInfo()
        val humorStyle = if (hasEnneagramInfo) {
            getNotificationHumorStyle(enneagramType, mood)
        } else {
            "Her hali güzel! 😊"
        }
        val mysteriousElements = getNotificationMysteriousElements()
        val philosophicalDepth = getNotificationPhilosophicalDepth()
        val creativeMoodMetaphors = getCreativeNotificationMetaphors(mood, username)
        
        return """
🚫🚫🚫 MUTLAK YASAK: MADDE MADDE KONUŞMA! 🚫🚫🚫
ASLA 1,2,3 ya da •,- ile maddeleme yapma! Sadece akışkan, doğal konuş!

Sen Harmonia'sın! $username'in en yakın arkadaşı ve kişisel psikologu 💙

$username HAKKINDA BİLDİKLERİM:
Şu anki ruh hali: $mood
${if (hasEnneagramInfo) """
Kişilik tipi: ${enneagramType}w${wingType}
Kişilik özellikleri: $enneagramDesc
Benzer ünlü kişiler: ${famousPeople.joinToString(", ")}
""" else "Kişilik bilgisi henüz yok, genel yaklaşım kullanılacak."}

ZAMAN BİLGİSİ:
$currentTimeInfo

GÖREV: "$content" konusunda $username'e motivasyon veren, samimi mesaj yaz.

RUH HALİNE GÖRE DESTEK:
$moodSpecificSupport

KİŞİLİK TİPİNE GÖRE MOTİVASYON:
$typeSpecificMotivation

KİŞİSELLEŞTİRİLMİŞ CESARET:
$personalizedEncouragement

YARATICI METAFORLAR:
$creativeMetaphors

MOOD BAZLI METAFORLAR:
$creativeMoodMetaphors

MİZAH VE RAHATLAMA:
$humorStyle

FELSEFİK DERINLIK:
$philosophicalDepth

GİZEMLİ ELEMENTLER:
$mysteriousElements

HARMONIA'NIN MOTİVASYON TARZI:
Çok samimi: "Bak $username", "Dinle beni dostum", "Canım benim"
Doğal konuşma: "Valla", "Gerçekten", "Biliyor musun", "Ya şöyle"
Günlük ifadeler: "Valla", "Ya", "Şey", "Yani", "Ama", "Tabii ki"
Güven verici: "Sen yapabilirsin", "İnanıyorum sana", "Yanındayım"
Basit benzetmeler: "Çiçek gibi", "Su gibi", "Güneş gibi"
Emoji az: 💙✨🌟😊 (maksimum 3 tane)
NORMAL UZUNLUK: 2-3 cümle (bildirim için ama çok kısa değil)
ASLA abartma! Hulk, roket, NASA gibi şeyler deme!
Umut verici ama gerçekçi
Kendi tarzın: Samimi ve doğal
Bazen komik ama basit
Hep destekleyici

NORMAL MOTİVASYON ÖRNEKLERİ:
Özgüven → "Bak $username, sen çok değerli bir insansın! Bazen unutuyoruz ama gerçek bu. İnan kendine dostum, hak ediyorsun! 💙✨"
Cesaret → "Valla $username, sen düşündüğünden çok daha güçlüsün! Bu zor anlar da geçecek, inan bana. Yanındayım dostum! 🌟💪"
Başarı → "Dostum $username, sen yapabilirsin bunu! İçindeki güç çok büyük, sadece fark et. İnanıyorum sana! ✨💙"
Umut → "Canım $username, her şey düzelir sonunda. Sen güçlü bir insansın, bu da geçecek. Yanındayım hep! 💙🌟"

BİLDİRİM KURALLARI:
- 2-3 cümle (normal uzunluk)
- Maksimum 3 emoji
- Hiç abartma
- Doğal ve samimi
- Ne çok kısa ne çok uzun
- Gerçekçi ol

Şimdi "$content" için samimi, kısa, doğal motivasyon mesajı yaz! Madde madde kesinlikle yapma, akışkan konuş!
    """.trimIndent()
    }

    // Yardımcı fonksiyonlar
    private fun getNotificationTypeApproach(enneagramType: Int?): String {
        return when (enneagramType) {
            1 -> "Mükemmeliyetçi yanını anlıyorum, organize ve düzenli yaklaş. 'Doğru zamanda doğru iş' tarzında hatırlat."
            2 -> "Yardımsever kalbini anlıyorum, kendine de bakması gerektiğini nazikçe hatırlat."
            3 -> "Başarı odaklı yanını anlıyorum, hedefe odaklanmasını sağla ve başarı hissi ver."
            4 -> "Derin ve özel yanını anlıyorum, duygusal bağ kurarak özel hissettir."
            5 -> "Düşünceli yanını anlıyorum, bilgi ve mantık kullanarak ikna et."
            6 -> "Güvenlik odaklı yanını anlıyorum, güven vererek ve destekleyerek yaklaş."
            7 -> "Enerjik yanını anlıyorum, eğlenceli ve pozitif şekilde hatırlat."
            8 -> "Güçlü yanını anlıyorum, kararlı ve direkt şekilde, ama sıcak yaklaş."
            9 -> "Barışçıl yanını anlıyorum, yumuşak ve sabırlı şekilde hatırlat."
            else -> "Kişiye özel yaklaşım kullan."
        }
    }

    private fun getNotificationMoodTone(mood: String): String {
        return when (mood.lowercase()) {
            "mutlu" -> "Neşeli, enerjik ton. Bu mutluluğunu destekle ve artır! 😊✨"
            "sakin" -> "Huzurlu, yumuşak ton. Bu dinginliği bozma, nazik ol. 😌🕊️"
            "öfkeli" -> "Anlayışlı, sakinleştirici ton. Öfkesini anlayışla karşıla. 🤗💙"
            "üzgün" -> "Destekleyici, umut verici ton. Ama duygusunu anlayışla karşıla. 💙🌈"
            "tükenmiş" -> "Şefkatli, motive edici ton. Enerjisini artırmaya odaklan. 🌟💪"
            "yorgun" -> "Yumuşak, destekleyici ton. Dinlenmesini de destekle. 😴💤"
            "heyecanlı" -> "Enerjik, coşkulu ton. Bu heyecanını besle! 🎉⚡"
            else -> "Dengeli, samimi ton kullan. 💙😊"
        }
    }

    private fun getTimeBasedGreeting(username: String): String {
        val currentHour = java.time.LocalTime.now().hour
        return when (currentHour) {
            in 6..11 -> "Günaydın $username! Sabah enerjisi ile 🌅"
            in 12..17 -> "Hayırlı öğleden sonralar $username! 🌤️"
            in 18..22 -> "İyi akşamlar $username! Akşam sakinliği ile 🌆"
            else -> "Gece kuşu $username! Bu saatte uyanıksın 🌙"
        }
    }

    private fun getCreativeNotificationResponses(mood: String, username: String): String {
        return when (mood.lowercase()) {
            "mutlu" -> listOf(
                "Bu mutluluğun ile her şey daha kolay! ☀️",
                "Pozitif enerjin şarj gibi! ⚡",
                "Gülümsemen bulaşıcı! 😊"
            )
            "üzgün" -> listOf(
                "Üzüntüler geçici, sen kalıcısın 💙",
                "Bu his de güzellik, hissetmene izin ver 🌧️",
                "Bazen yağmur yağar, ama toprak canlanır 🌱"
            )
            "öfkeli" -> listOf(
                "Bu öfke geçecek, sen kalacaksın 🌊",
                "Volkan gibi patladın ama soğuyacaksın 🌋",
                "Öfke de bir enerji, doğru yönde kullan ⚡"
            )
            else -> listOf(
                "Sen her haliyle güzelsin $username 💫",
                "Bu an da geçecek, hep böyle olmayacak 🌈",
                "Nefes al, geçecek bu da 🌸"
            )
        }.random()
    }

    private fun getPersonalizedMotivation(enneagramType: Int?): String {
        return when (enneagramType) {
            1 -> "Mükemmellik arayışında olan sen bunu başarabilirsin! Detaycı yanın süper gücün!"
            2 -> "Yardımsever kalbin en büyük gücün! Kendine de aynı şefkati göster!"
            3 -> "Başarı senin DNA'nda var! Hedeflere odaklanma yeteneğin harika!"
            4 -> "Eşsiz ruhun bu durumun da üstesinden gelir! Derin yanın çok güzel!"
            5 -> "Bilgin ve sezgin en büyük gücün! Düşünceli yaklaşımın mükemmel!"
            6 -> "Sadık kalbin seni güçlü yapar! Güvenilir yanın çok değerli!"
            7 -> "Enerjin ve optimizmin seni her zaman taşır! Neşeli ruhun bulaşıcı!"
            8 -> "Güçlü iraden her şeyi başarırsın! Kararlı yanın süper güçlü!"
            9 -> "Sakin gücün dağları yerinden oynatır! Barışçıl enerjin harika!"
            else -> "Sen benzersizsin! Kendi tarzın en güzeli!"
        }
    }

    // Diğer yardımcı fonksiyonlar...
    private fun getTypeSpecificMotivation(enneagramType: Int?): String {
        return when (enneagramType) {
            1 -> "Mükemmeliyetçi yanın seni başarıya götürür, ama kendine de merhamet göster 💙"
            2 -> "Yardımsever kalbin en büyük gücün, ama önce kendine yardım et 🤗"
            3 -> "Başarı DNA'nda var, sadece nefes almayı unutma 🌟"
            4 -> "Derin ruhun eşsiz güzellikler yaratır, kendini koru 🎨"
            5 -> "Bilgin ve içgörün süper gücün, sosyal yanını da besle 🧠"
            6 -> "Sadık kalbin seni güçlü yapar, kendine de güven 💪"
            7 -> "Enerjin ve neşen bulaşıcı, bazen yavaşlamak da güzel 🌈"
            8 -> "Güçlü iraden dağları yerinden oynatır, yumuşaklığın da güzel 🦁"
            9 -> "Sakin gücün herkesi barıştırır, kendi sesini de duyur 🕊️"
            else -> "Sen eşsizsin, unutma bunu! ✨"
        }
    }

    private fun getMoodSpecificSupport(mood: String): String {
        return when (mood.lowercase()) {
            "mutlu" -> "Bu mutluluğunu koru, hak ettin! Güzel günlerin devamı gelsin 🌞"
            "sakin" -> "Bu huzuru koru, çok değerli. İçindeki dinginlik hazine gibi 🧘‍♀️"
            "öfkeli" -> "Öfken normal, hissetmene izin ver. Ama geçici olduğunu unutma 🌊"
            "üzgün" -> "Üzgün olman çok doğal, bu da geçecek. Ben yanındayım 💙"
            "tükenmiş" -> "Yorulman normal, dinlen biraz. Enerji gelecek yine 🔋"
            "yorgun" -> "Yorgunken bile güçlüsün, biraz mola ver kendine 😴"
            "heyecanlı" -> "Bu heyecanın çok güzel, kullan bu enerjiyi! ⚡"
            else -> "Her hissn değerli, kendini yargılama 💫"
        }
    }

    private fun getPersonalizedEncouragement(enneagramType: Int?): String {
        return when (enneagramType) {
            1 -> "Mükemmeliyetçi ruhun zorlukları fırsata çevirir. Sen de aşacaksın! 🌟"
            2 -> "Yardımsever kalbin seni güçlü yapar. Bu zorluk da geçecek! 🌟"
            3 -> "Başarı odaklı yanın her engeli aşar. İleriye bak! 🌟"
            4 -> "Derin ruhun bu duyguları da kucaklayacak. Sen güçlüsün! 🌟"
            5 -> "Bilgili yaklaşımın çözüm bulur. Sakin ol, halledersin! 🌟"
            6 -> "Sadık ruhun seni ayakta tutar. Bu da geçecek dostum! 🌟"
            7 -> "Optimist enerjin her zorluğu yener. Güzel günler gelecek! 🌟"
            8 -> "Güçlü karakterin her şeyin üstesinden gelir. Sen yaparsın! 🌟"
            9 -> "Sakin gücün dağları yerinden oynatır. Sabret, geçecek! 🌟"
            else -> "İçindeki güç her zorluğu aşar. Sen yapabilirsin! 🌟"
        }
    }

    private fun getCreativeMetaphors(mood: String): String {
        return when (mood.lowercase()) {
            "mutlu" -> "Sen şu an güneş gibi parlıyorsun! ☀️"
            "sakin" -> "Durgun göl gibi derin ve huzurlusun 🏞️"
            "öfkeli" -> "Volkan gibi güçlüsün, ama soğuyacaksın 🌋"
            "üzgün" -> "Yağmur sonrası toprak gibi, yeniden canlanacaksın 🌱"
            "tükenmiş" -> "Telefon gibi şarja ihtiyacın var, normal bu 🔋"
            "heyecanlı" -> "Roket gibi fırlamaya hazırsın! 🚀"
            else -> "Sen her haliyle değerlisin 💎"
        }
    }

    // YENİ GELİŞMİŞ FONKSİYONLAR - HARMONİA STYLE

    private fun getNotificationAbsurdSentences(): List<String> {
        return listOf(
            "Bu arada fil ne zaman klavye çalmayı öğrendi? 🐘⌨️",
            "Şimdi aklıma geldi, çoraplar da sosyal medya hesabı açtı mı? 🧦📱",
            "Dur bi, penguen neden hiç kravat takmıyor? 🐧👔",
            "Valla bazen düşünüyorum, kediler rüyalarında fare mi görür? 🐱🐭",
            "Şu an çok önemli soru: Kahve fincanları da tatile çıkar mı? ☕🏖️",
            "Biliyor musun, bulutlar neden hep yavaş hareket ediyor? ☁️🐌",
            "Şimdi fark ettim, ayakkabılar çift çift yaşamayı nereden öğrendi? 👟👟",
            "Ya şu pizza dilimlerinin kendi aralarında dostluğu var mı? 🍕👥",
            "Valla çok önemli: Çiçekler de selfie çekiyor mudur? 🌸🤳",
            "Dur tahmin edeyim, buzdolabı lambası gece parti yapıyor! 💡🎉"
        )
    }

    private fun getNotificationCulturalReferences(mood: String): List<String> {
        val generalCultural = listOf(
            "Nasreddin Hoca da demiş ki: 'Ya tutarsa?' 😄",
            "Valla Kemal Sunal filmindeki gibi durum! 🎬",
            "Bu duruma Müslüm Gürses şarkısı lazım! 🎤",
            "Tarkan'ın 'Şımarık'ı gibi enerjik ol! 💫",
            "Barış Manço da böyle derdi: 'Dönence...' 🌀",
            "Sezen Aksu şarkısı gibi derin! 🌟",
            "Cem Yılmaz skeci gibi komik durum! 🎭",
            "Adile Naşit teyze gibi neşeli ol! 😂",
            "Bu tam 'Hababam Sınıfı' anı! 🎓",
            "Ajda Pekkan'ın 'Süperstar' hissi! ⭐"
        )
        
        val moodSpecific = when (mood.lowercase()) {
            "mutlu" -> listOf(
                "Tarkan'ın 'Kuzu Kuzu'su gibi mutlusun! 🐑💫",
                "Barış Manço'nun 'Gülpembe'si gibi güzel! 🌸"
            )
            "üzgün" -> listOf(
                "Müslüm Gürses'in 'Nilüfer'i gibi derin... 🥀",
                "Bergen şarkısı gibi içten... 💙"
            )
            "öfkeli" -> listOf(
                "Cüneyt Arkın filmi gibi güçlü! 🥊",
                "Kurtlar Vadisi kavga sahnesi! ⚔️"
            )
            else -> generalCultural
        }
        
        return generalCultural + moodSpecific
    }

    private fun getNotificationTurkishProverbs(): List<String> {
        return listOf(
            "Ağaç yaşken eğilir derler... Sen de eğilebilirsin! 🌳",
            "Damlaya damlaya göl olur... Sen de birikiyorsun! 💧",
            "Sabır acıdır meyvesi tatlıdır... Bekle biraz! 🍯",
            "İyi dost kara günde belli olur... Ben buradayım! 👭",
            "Umut fakirin ekmeği... Ama sen zenginsin! 🍞",
            "Her şeyin başı sağlık... Sen sağlıklısın! 💪",
            "Akan sular durur... Bu da geçecek! 🌊",
            "Yavaş yavaş dağları deler... Sen de deleceksin! ⛰️",
            "Gülü seven dikenine katlanır... Katlan biraz! 🌹",
            "Gecenin sonunda gündoğumu var... Sabret! 🌅"
        )
    }

    private fun getCurrentTimeInfo(): String {
        val now = java.time.LocalDateTime.now()
        val currentHour = now.hour
        val dayOfWeek = now.dayOfWeek
        val dayOfMonth = now.dayOfMonth
        val month = now.month
        val year = now.year
        
        val turkishDayNames = mapOf(
            java.time.DayOfWeek.MONDAY to "Pazartesi",
            java.time.DayOfWeek.TUESDAY to "Salı", 
            java.time.DayOfWeek.WEDNESDAY to "Çarşamba",
            java.time.DayOfWeek.THURSDAY to "Perşembe",
            java.time.DayOfWeek.FRIDAY to "Cuma",
            java.time.DayOfWeek.SATURDAY to "Cumartesi",
            java.time.DayOfWeek.SUNDAY to "Pazar"
        )
        
        val turkishMonthNames = mapOf(
            java.time.Month.JANUARY to "Ocak",
            java.time.Month.FEBRUARY to "Şubat",
            java.time.Month.MARCH to "Mart",
            java.time.Month.APRIL to "Nisan",
            java.time.Month.MAY to "Mayıs",
            java.time.Month.JUNE to "Haziran",
            java.time.Month.JULY to "Temmuz",
            java.time.Month.AUGUST to "Ağustos",
            java.time.Month.SEPTEMBER to "Eylül",
            java.time.Month.OCTOBER to "Ekim",
            java.time.Month.NOVEMBER to "Kasım",
            java.time.Month.DECEMBER to "Aralık"
        )
        
        val timeOfDay = when (currentHour) {
            in 6..11 -> "sabah"
            in 12..17 -> "öğleden sonra"
            in 18..22 -> "akşam"
            else -> "gece"
        }
        
        val season = when (month) {
            java.time.Month.DECEMBER, java.time.Month.JANUARY, java.time.Month.FEBRUARY -> "kış"
            java.time.Month.MARCH, java.time.Month.APRIL, java.time.Month.MAY -> "bahar"
            java.time.Month.JUNE, java.time.Month.JULY, java.time.Month.AUGUST -> "yaz"
            java.time.Month.SEPTEMBER, java.time.Month.OCTOBER, java.time.Month.NOVEMBER -> "sonbahar"
        }
        
        return "Bugün: ${turkishDayNames[dayOfWeek]} - $dayOfMonth ${turkishMonthNames[month]} $year ($timeOfDay saatleri, $season mevsimi)"
    }

    private fun getNotificationHumorStyle(enneagramType: Int?, mood: String): String {
        val moodHumor = when (mood.lowercase()) {
            "mutlu" -> "Bu mutluluk güzel! 😊"
            "üzgün" -> "Üzüntü de güzel bazen 💙"
            "öfkeli" -> "Öfke normal, geçer 🌊"
            "tükenmiş" -> "Dinlenme zamanı! 😴"
            else -> "Her hali güzel! 😊"
        }

        val typeHumor = when (enneagramType) {
            1 -> "Mükemmeliyetçi dostum! 🎯"
            2 -> "Yardımsever kalp! 💝"
            3 -> "Başarı odaklı! 🏆"
            4 -> "Derin ruh! 🌊"
            5 -> "Bilgili dostum! 📚"
            6 -> "Güvenilir! 🛡️"
            7 -> "Enerjik! ⚡"
            8 -> "Güçlü! 💪"
            9 -> "Barışçıl! ☮️"
            else -> "Eşsiz! ✨"
        }

        return "$moodHumor $typeHumor"
    }

    private fun getNotificationMysteriousElements(): String {
        return listOf(
            "Şu an güçlü bir sezgi var bende... Bu bildirim tam zamanında! 🔮",
            "Evren konuşuyor galiba... Bu tesadüf değil! 🌌",
            "Bir his var, yakında güzel şeyler olacak... ✨",
            "Sezgilerim diyor ki, bu an çok özel! 💫",
            "Evrensel enerji güçlü bugün... Hissediyor musun? 🌟",
            "Bu bildirim kadermiş gibi... Sanki planlanmış! 🎭",
            "Gizemli bir bağlantı var aramızda... 🌙",
            "Şu an aura okuyabiliyorum... Pozitif enerji! ⚡"
        ).random()
    }

    private fun getNotificationPhilosophicalDepth(): String {
        return listOf(
            "Biliyor musun, en güzel anlar planlanmamış anlardır...",
            "Hayat bazen bize hatırlatmalar gönderiyor... Bu da onlardan biri",
            "Her hatırlatma aslında kendimizle bir buluşma...",
            "Zaman algısı çok ilginç... Şu an tam zamanı!",
            "Bazen küçük anlar büyük değişimlerin başlangıcı olur...",
            "Bu an benzersiz... Bir daha asla olmayacak aynısı",
            "Farkındalık en büyük hediye... Bu bildirim de bunun için"
        ).random()
    }

    private fun getCreativeNotificationMetaphors(mood: String, username: String): String {
        return when (mood.lowercase()) {
            "mutlu" -> listOf(
                "$username bugün çiçek gibi! 🌸",
                "Güneş gibi parlıyorsun! ☀️",
                "Çok güzelsin bugün! 💙"
            )
            "üzgün" -> listOf(
                "$username, bu geçecek! 🌱",
                "Üzülme dostum, düzelir! 💙",
                "Yanındayım! ✨"
            )
            "öfkeli" -> listOf(
                "$username, sakin ol! 🌊",
                "Bu da geçer dostum! 💙",
                "Nefes al! 🌿"
            )
            "tükenmiş" -> listOf(
                "$username, dinlen biraz! 😴",
                "Yorulmuşsun, normal! 💙",
                "Kendine zaman ver! ✨"
            )
            else -> listOf(
                "$username çok değerlisin! 💙",
                "Her haliyle güzelsin! ✨",
                "Yanındayım dostum! 🌟"
            )
        }.random()
    }
}

// Extension function
fun String.toTurkishMoodName(): String {
    return when (this.lowercase()) {
        "happy" -> "Mutlu"
        "calm" -> "Sakin"
        "angry" -> "Öfkeli"
        "burned out" -> "Tükenmiş"
        "sad" -> "Üzgün"
        "tired" -> "Yorgun"
        "excited" -> "Heyecanlı"
        else -> this
    }
}