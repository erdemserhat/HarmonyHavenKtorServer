package com.erdemserhat.service

import com.erdemserhat.dto.requests.FcmNotification
import com.erdemserhat.dto.requests.SendNotificationSpecific
import com.erdemserhat.dto.requests.toFcmMessage
import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.service.openai.OpenAIRequest
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import java.lang.System.err
import kotlin.coroutines.coroutineContext

suspend fun sendAIBasedMessage(
    notificationAI: NotificationAI,
    sendSpecificByEmailList: List<String> = emptyList()

) {
    //you must be inside a corutine scope to start a coroutine (async and launch)
    coroutineScope {
        var body = ""

        do {
            body = OpenAIRequest(notificationAI.prompt).getResult().replace(Regex("Ali"), "*name")
        } while (!validateAIResponse(body))


        val title = notificationAI.title


        val fcmNotification = FcmNotification(
            title = title,
            body = body,
            image = "",
            screen = "1"
        )

        if (sendSpecificByEmailList.isEmpty()) {
            val deferredUser = async(Dispatchers.IO){
                DatabaseModule.userRepository.getAllUsers().filter { it.fcmId.length > 10 }
            }
            val semaphore = Semaphore(10) //maximum 10 coroutine
            deferredUser.await().forEach {
                //creates new coroutines for every request as asynchronously which is child of coroutineScope
                launch() {
                    semaphore.acquire() // take semaphore
                    try {
                        val specificNotification = SendNotificationSpecific(it.email, fcmNotification)
                        FirebaseMessaging.getInstance().send(specificNotification.toFcmMessage())
                    } catch (e: Exception) {
                        err.println("sendAIBasedMessage(): ${e.message}")
                    } finally {
                        semaphore.release() //release semaphore
                    }
                }
            }


        } else {

            val semaphore = Semaphore(10)
            sendSpecificByEmailList.forEach { email ->
                launch {
                    semaphore.acquire()
                    try {
                        val specificNotification = SendNotificationSpecific(
                            email, fcmNotification
                        )
                        FirebaseMessaging.getInstance().send(specificNotification.toFcmMessage())
                    } catch (e: Exception) {
                        err.println("sendAIBasedMessage(): ${e.message}")
                    } finally {
                        semaphore.release() //release semaphore
                    }

                }
            }


        }

    }
}

private fun validateAIResponse(message: String): Boolean {
    val disallowedPhrases = listOf(
        "Elbette!", "Tabii ki!", "Kesinlikle!", "Tabii!", "Peki!",
        "Elbette ki!", "Anlaşıldı!", "Tabii tabii!", "Memnuniyetle!",
        "Doğal olarak!", "Aynen öyle!", "Şüphesiz!", "Tabi ki!", "Tabii efendim!"
    )

    // Eğer mesaj, yasaklı giriş cümlelerinden birini içeriyorsa false döner
    return disallowedPhrases.none { message.contains(it, ignoreCase = true) }
}

object NotificationAICategories {
    private val titleListCommon = arrayOf(
        "Kısa bir hatırlatma *name ⭐✨",
        "*name.. 🌟",
        "*name, küçük bir not: 🎯",
        "✨ *name, sana özel bir hatırlatma: 💫",
        "Bugün senin günün *name! 🌞",
        "*name... 🌟 Unutma ki, sen çok güçlüsün! 💪",
        "Küçük bir hatırlatma, *name! 💡",
        "✨ *name, bugün her şey mümkün! 🚀",
        "Gün senin günün, *name! 🌅",
        "🌸 *name, sana ilham verecek bir not! ✨",
        "*name, harika şeyler seni bekliyor! 🌟",
        "Kendine güven, *name! Sen başaracaksın! 💪",
        "*name, hep ileriye! 🏆",
        "Bir adım daha, *name! 🌟",
        "Parla *name! Bugün senin zamanın! ✨",
        "*name, hatırlatmak istedim: Sen harikasın! 🌟",
        "Unutma *name, başarı çok yakın! 🔥"
    )
    val list = listOf(
        advice(),
        //healthAndWellness(),
        mindfulness(),
        careerAndProductivity(),
        //healthAndWellness(),
        spirituality(),
        //learningAndKnowledge(),
        personalGrowth(),
        philosophyAndWisdom(),
        //hobbiesAndCreativity(),
        motivation()

    )

    fun motivation(): NotificationAI {

        val toneList = arrayOf(
            "enerjik ve motive edici",
            "pozitif ve cesaretlendirici",
            "ciddi ve odaklanmış",
            "yapıcı ve rehberlik eden",
            "güç verici ve ilham veren",
            "azimli ve kararlı",
            "yumuşak ve anlayışlı",
            "olumlu ve motive edici",
            "cesur ve kararlı",
            "bilgece ve sakinleştirici",
            "teşvik edici ve motive edici",
            "düşündürücü ve anlamlı",
            "heveslendirici ve umut verici",
            "samimi ve güven verici",
            "iyimser ve neşeli",
            "huzur verici ve rahatlatıcı",
            "kararlı ve ileri görüşlü",
            "kendinden emin ve yol gösterici",
            "şefkatli ve empatik",
            "cesaretlendirici ve destekleyici",
            "hırslandırıcı ve motive edici",
            "dinamik ve ilham verici",
            "umut dolu ve yol gösterici",
            "yenilikçi ve yaratıcı",
            "başarıya odaklanmış ve kararlı",
            "sınırları zorlayan ve motive eden",
            "vizyoner ve cesur",
            "galibiyete odaklanmış ve hırslı",
            "iyimser ve geleceğe umutla bakan",
            "engel tanımayan ve azimli",
            "her şeye rağmen başaran ve güçlü",
            "öncü ve başarıya adanmış",
            "umut dolu ve başarıyı hedefleyen",
            "asla pes etmeyen ve kararlı",
            "kendine güvenen ve ileriye bakan",
            "tutkulu ve hedef odaklı",
            "yıldızlara ulaşmayı hedefleyen",
            "başarıya aç ve motive edici",
            "her zorluğa meydan okuyan",
            "güven dolu ve zafer odaklı"
        )

        val emotionList = arrayOf(
            "yorgun",
            "mutsuz",
            "motivasyonsuz",
            "kaygılı",
            "enerjik",
            "umutlu",
            "kendinden emin",
            "şüpheci",
            "heyecanlı",
            "endişeli",
            "mutlu",
            "huzurlu",
            "kararsız",
            "hevesli",
            "stresli"
        )


        val randomTone = toneList.random()
        val randomEmotion = emotionList.random()
        val randomTitle = titleListCommon.random()

        val prompt =
            "$randomEmotion hissettiğimde okuyabileceğim, teması motivasyon olan $randomTone bir mesaj yaz."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun islamic(): NotificationAI {
        val subjectList = arrayOf(
            "sabır ve tevekkül",
            "iman ve ibadet",
            "takva",
            "tevazu",
            "yardımlaşma",
            "kul hakkı",
            "ahlak",
            "güzel ahlak",
            "şükretmek",
            "zamanın kıymeti",
            "ilme verilen değer",
            "ibadet ve dünya dengesi",
            "dua ve Allah'a yönelme",
            "sadaka ve hayır işleri",
            "hoşgörü ve affetmek",
            "cömertlik",
            "sabırla başarıya ulaşma",
            "Allah'a güven",
            "helal kazanç",
            "Allah'ın rızası"
        )

        val toneList = arrayOf(
            "huzur verici ve ibret dolu",
            "düşündürücü ve manevi derinlikte",
            "şefkatli ve rehberlik eden",
            "tevazu dolu ve sakinleştirici",
            "sabırlı ve cesaretlendirici",
            "güzel ahlakı vurgulayan",
            "yardımsever ve merhametli",
            "Allah'a yönlendiren",
            "iman dolu ve umut veren",
            "hikmet dolu ve düşündürücü"
        )

        val titleList = arrayOf(
            "*name, Allah'ın rahmeti seninle olsun! 🌸",
            "*name, Allah'ın huzuru seninle! ✨",
            "*name, günün anlamını hatırlatan bir not: 🌹",
            "🌟 *name, Allah'ın huzurunu hissetmen için: 🌼",
            "*name, Allah'ın verdiği güçle parlamaya devam et! ✨",
            "🌙Allah'ın sevgisiyle dolu bir gün dilerim! 🌺",
            "Allah'ın rahmeti ve desteği seninle olsun 🌷",
            "Huzurlu günler *name! 🌼",
            "*name, manevi bir not! 🌸",

            )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "Bana $randomSubject konusunda $randomTone bir İslami tavsiye ver."
        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }


    fun advice(): NotificationAI {
        val subjectList = arrayOf(
            "hayat",
            "insanlar",
            "başarı",
            "zaman yönetimi",
            "zorluklarla başa çıkma",
            "motivasyon",
            "dostluk",
            "özgüven",
            "hedef belirleme",
            "kariyer",
            "aile",
            "mutluluk",
            "sağlık",
            "disiplin",
            "sabır",
            "liderlik",
            "empati",
            "çalışma disiplini",
            "risk alma",
            "başarısızlıkla başa çıkma"
        )

        val toneList = arrayOf(
            "bilgece ve sakinleştirici",
            "düşündürücü ve anlamlı",
            "pozitif ve cesaretlendirici",
            "ciddi ve odaklanmış",
            "yapıcı ve rehberlik eden",
            "şefkatli ve empatik",
            "cesur ve kararlı",
            "iyimser ve neşeli",
            "cesaretlendirici ve destekleyici",
            "vizyoner ve cesur"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()

        val prompt = "$randomSubject ile ilgili $randomTone  bir tavsiye ver."

        return NotificationAI(
            prompt = prompt,
            title = titleListCommon.random()
        )
    }

    fun goodNight(): NotificationAI {
        val toneList = arrayOf(
            "sakinleştirici ve huzur verici",
            "ilham verici ve umut dolu",
            "rahatlatıcı ve sevgi dolu",
            "pozitif ve iyi niyetli",
            "güçlendirici ve destekleyici"
        )

        val titleList = arrayOf(
            "İyi Geceler *name! 🌙",
            "Tatlı Rüyalar *name! ✨",
            "Huzurlu Bir Gece Geçir *name! 🌟",
            "Geceyi Huzurla Geçir *name! 🌠",
            "*name, Geceyi Huzurla Kapat! 🌙",
            "İyi Uykular *name! 🌛",
            "🌙 Yıldızlarla Dolu Bir Gece Senin Olsun! ✨",
            "💤 Gece Senin İçin Huzurla Dolsun. 🌟",
            "🌌 Senin İçin Yıldızlar Parlasın. 🌙",
            "🌟Tatlı Rüyalar! ✨",
        )

        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir iyi geceler mesajı yaz."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun goodMorning(): NotificationAI {
        val toneList = arrayOf(
            "neşeli ve enerjik",
            "ilham verici ve umut dolu",
            "pozitif ve motive edici",
            "rahatlatıcı ve iç açıcı",
            "güçlendirici ve motive edici"
        )

        val titleList = arrayOf(
            "Günaydın *name! ☀️",
            "Harika Bir Gün Olsun *name! 🌞",
            "Enerjik Bir Sabah *name! 🌅",
            "Güne Güzel Başla *name! 🌟",
            "Yeni Bir Gün, Yeni Fırsatlar *name! 🌻",
            "Pozitif Bir Gün Senin Olsun *name! 🌈",
            "🌞 Mutlu Sabahlar *name! ✨",
            "Başarı Dolu Bir Gün Senin Olsun *name! 🚀",
            "Güne Gülümseyerek Başla *name! 😄",
            "🌅 Yeni Gün, Yeni Umutlar *name! 🌞"
        )

        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir günaydın mesajı yaz."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun healthAndWellness(): NotificationAI {
        val subjectList = arrayOf(
            "düzenli egzersiz yapmanın faydaları",
            "sağlıklı beslenme önerileri",
            "daha iyi uyku için ipuçları",
            "stres yönetimi yöntemleri",
            "hidrasyonun önemi",
            "mental sağlığı koruma",
            "beden ve zihin dengesi",
            "sağlıklı alışkanlıklar oluşturma",
            "sabah rutininin faydaları",
            "doğal yollarla enerjiyi artırma"
        )

        val toneList = arrayOf(
            "canlandırıcı ve motive edici",
            "rahatlatıcı ve destekleyici",
            "bilgilendirici ve ilham verici",
            "pozitif ve yönlendirici",
            "dengeli ve sağlıklı yaşamı teşvik eden"
        )

        val titleList = arrayOf(
            "Sağlık İpuçları *name! 💪",
            "Zinde Kalmanın Yolları *name! 🌟",
            "Bugün Sağlığın İçin Ne Yapabilirsin? 🌿",
            "*name, Enerjini Yüksek Tut! ⚡",
            "Güçlü ve Sağlıklı Kal, *name! 🍎",
            "Bedenine İyi Bak, *name! 🌼",
            "Huzurlu ve Zinde Bir Gün Dileriz! 🌞",
            "Sağlıklı Yaşam, Mutlu Yaşam *name! 💚"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir mesaj ile $randomSubject hakkında bilgi ver."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun mindfulness(): NotificationAI {
        val subjectList = arrayOf(
            "şu anın tadını çıkarma",
            "derin nefes almanın önemi",
            "zihni sakinleştirme teknikleri",
            "anda kalma pratiği",
            //"stresi azaltmak için mindfulness",
            "zihinsel berraklığı artırma",
            "düşüncelerini gözlemleme",
            //"günlük mindfulness alışkanlıkları",
            "beden farkındalığı"
        )

        val toneList = arrayOf(
            "sakinleştirici ve huzur verici",
            "rahatlatıcı ve farkındalık dolu",
            "pozitif ve yönlendirici",
            "dingin ve meditasyon dolu",
            "düşündürücü ve bilgece"
        )

        val titleList = arrayOf(
            "Anda Kal *name! 🧘‍♀️",
            "Farkındalık Zamanı *name! 🌿",
            "Şu Anın Tadını Çıkar *name! 🌸",
            "Zihnini Sakinleştir *name! 🌙",
            "Derin Bir Nefes Al *name! 🌬️",
            "Huzur Bul *name! 🌼",
            "Anı Yaşa *name! 💫",
            "Dinginlik ve Farkındalık *name! 🧘‍♂️",
            "Bugün Anda Kal! *name 🧠",
            "Zihnine Huzur Ver *name! 🌟"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir mesaj ile $randomSubject hakkında bilgi ver."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun relationshipAdvice(): NotificationAI {
        val subjectList = arrayOf(
            "sağlıklı iletişim kurma",
            "güven inşa etme",
            "saygı ve empati",
            "ilişkide dengeyi bulma",
            "birbirini dinlemenin önemi",
            "anlayışlı olma",
            "ilişkilerde sabırlı olma",
            "destekleyici olma",
            "sevgiyi ifade etmenin yolları",
            "ilişkiyi güçlendirme"
        )

        val toneList = arrayOf(
            "yapıcı ve samimi",
            "sevgi dolu ve şefkatli",
            "cesaretlendirici ve bilgece",
            "yumuşak ve empatik",
            "ilham verici ve olumlu"
        )

        val titleList = arrayOf(
            "İlişkide Güven *name! 🤝",
            "Sağlıklı İletişim Kur *name! 🗣️",
            "Anlayışlı Olmak Önemlidir *name! 💞",
            "Sevgi ve Empati *name! ❤️",
            "İlişkini Güçlendir *name! 🌟",
            "Birbirinizi Dinleyin *name! 👂",
            "İlişkide Dengeyi Bul *name! ⚖️",
            "Destekleyici Ol *name! 🌷",
            "Sevgiyi İfade Et *name! 💌",
            "Birlikte Büyüyün *name! 🌱"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir mesaj ile $randomSubject hakkında tavsiye ver."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun careerAndProductivity(): NotificationAI {
        val subjectList = arrayOf(
            "zaman yönetimi",
            "hedef belirleme",
            "odaklanma",
            "verimlilik artırma",
            "kariyer gelişimi",
            "iş-yaşam dengesi",
            "kendini motive etme",
            "yapıcı eleştiri alma",
            "başarı için planlama",
            "işte problem çözme"
        )

        val toneList = arrayOf(
            "motivasyon verici ve cesaretlendirici",
            "odaklanmış ve rehberlik eden",
            "ilham verici ve yapıcı",
            "pozitif ve yön gösterici",
            "sakin ve çözüm odaklı"
        )

        val titleList = arrayOf(
            "Başarıya Bir Adım Daha *name! 🚀",
            "Hedeflerine Ulaş *name! 🎯",
            "Zamanını İyi Yönet *name! ⏳",
            "Verimliliği Artır *name! ⚡",
            "Kariyerinde Bir Adım İleri *name! 🌟",
            "Odaklan ve Başar *name! 🔍",
            "Kendini Motive Et *name! 💪",
            "İş-Yaşam Dengesi Sağla *name! ⚖️",
            "Başarı İçin Plan Yap *name! 📅",
            "Problem Çözme Yeteneğini Geliştir *name! 🧠"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir mesaj ile $randomSubject hakkında kariyer ve verimlilik tavsiyesi ver."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun hobbiesAndCreativity(): NotificationAI {
        val subjectList = arrayOf(
            "hobilerin gücü",
            "yaratıcılığını geliştirmek",
            "sanat ve kişisel ifade",
            "yeni beceriler öğrenmek",
            "hayal gücünü kullanmak",
            "hobilerin ruh sağlığına etkisi"
        )

        val toneList = arrayOf(
            "ilham verici ve cesaretlendirici",
            "pozitif ve yaratıcı",
            "neşeli ve motive edici",
            "bilgilendirici ve teşvik edici",
            "sakinleştirici ve ruhsal"
        )

        val titleList = arrayOf(
            "*name, Hobilerle Hayatını Renklendir! 🎨",
            "Yaratıcılığını Keşfet *name! 🌟",
            "*name, Sanat ve Hobilerin Gücü! ✨",
            "Hayal Gücünü Kullan *name! 🚀",
            "Hobilerle Ruhunu Besle *name! 🎭",
            "Yaratıcılığınla Parla *name! 💡",
            "Yeni Beceriler Öğrenmek İçin *name!'a İlham! 🌠",
            "Hobilerle Dolu Bir Gün Senin Olsun *name! 🎨",
            "*name, Yaratıcılığınla Dünyayı Keşfet! 🌍",
            "Sanat ve İfade Özgürlüğü *name!'a! 🎨"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir mesaj ile $randomSubject hakkında yaratıcı tavsiyeler ver."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun spirituality(): NotificationAI {
        val subjectList = arrayOf(
            "içsel huzur",
            "manevi gelişim",
            "ruhsal denge",
            "kader ve kabul",
            "manevi bağlılık",
            "içsel yolculuk"
        )

        val toneList = arrayOf(
            "huzur verici ve derin",
            "ilham verici ve anlamlı",
            "şefkatli ve bilgilendirici",
            "rahatlatıcı ve manevi",
            "motive edici ve düşündürücü"
        )

        val titleList = arrayOf(
            "*name, İçsel Huzuruna Yolculuk! 🌟",
            "Ruhsal Dengen için *name!'a Tavsiye 🌌",
            "*name, Manevi Gelişimine Katkıda Bulun! 🌿",
            "İçsel Yolculuğunla Parla *name! 🌠",
            "*name, Manevi Yükselişin İçin İlham! 🌈",
            "İçsel Sakinliğe Ulaş *name!🌟",
            "Kaderini Kabullen *name! 🌟"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir mesaj ile $randomSubject hakkında ruhsal tavsiyeler ver."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun learningAndKnowledge(): NotificationAI {
        val subjectList = arrayOf(
            "kişisel gelişim",
            "yeni beceriler öğrenme",
            "kitap okuma",
            "eğitim ve öğrenim",
            "akademik başarı",
            "öğrenme stratejileri",
            "kendini geliştirme",
            "beyin egzersizleri",
        )

        val toneList = arrayOf(
            "ilham verici ve öğretici",
            "düşündürücü ve motive edici",
            "bilgi dolu ve yönlendirici",
            "pozitif ve cesaretlendirici",
            "akademik ve aydınlatıcı"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()

        val prompt = "$randomSubject ile ilgili $randomTone bir tavsiye ver."

        return NotificationAI(
            prompt = prompt,
            title = "Öğrenme ve Bilgi"
        )
    }

    fun personalGrowth(): NotificationAI {
        val subjectList = arrayOf(
            "öz disiplin",
            "hedeflere ulaşma",
            "kişisel hedefler",
            "öz farkındalık",
            "zihinsel güçlenme",
            "kişisel başarı",
            "kendi potansiyelini keşfetme",
            "kişisel değişim",
            "kendini tanıma",
            "motivasyon ve azim"
        )

        val toneList = arrayOf(
            "cesaretlendirici ve ilham verici",
            "pozitif ve motive edici",
            "bilgi dolu ve yönlendirici",
            "kararlı ve güçlendirici",
            "özgüven artırıcı ve destekleyici"
        )

        val titleList = arrayOf(
            "*name, Kişisel Gelişim Yolunda: 🚀",
            "Kişisel Büyüme İçin Bir İpucu: *name! 🌟",
            "Gelişim Yolunda Adım At! *name! 💪",
            "Öz Disiplin ve Başarı İçin: *name! 🏆",
            "*name, Kişisel Başarı İçin İlham Verici Bir Mesaj! ✨",
            "Kendini Keşfetmeye Hazır Mükemmel Bir Not! *name! 🌈"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomSubject ile ilgili $randomTone bir tavsiye ver."

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }

    fun philosophyAndWisdom(): NotificationAI {
        val subjectList = arrayOf(
            "hayatın anlamı",
            "bilgelik ve deneyim",
            "varoluşsal sorular",
            "felsefi düşünceler",
            "derin yaşam dersleri",
            "felsefi alıntılar",
            "insan doğası",
            "sokratesçi sorgulama",
            "akıl ve mantık",
            "özgür irade"
        )

        val toneList = arrayOf(
            "derin ve düşündürücü",
            "bilgece ve aydınlatıcı",
            "sakinleştirici ve anlamlı",
            "ilham verici ve düşündüren",
            "felsefi ve entelektüel",
            "zihni uyandırıcı",
            "yapıcı ve anlamlı",
            "zengin ve öğretici",
            "sorgulayıcı ve cesur",
            "düşünceye teşvik edici"
        )

        val titleList = arrayOf(
            "Bilgelik Işığında: *name",
            "*name, Derin Düşünceler Senin İçin",
            "Felsefi Bir Dokunuş *name",
            "Hayatın Anlamı Üzerine *name",
            "*name, Bilgelik ve Öğreti",
            "Düşünce Yolculuğu *name",
            "*name, Bilgelikten İlham Al",
            "Sorgulayıcı Düşünceler *name",
            "Derin Bilgelik Seninle *name",
            "Felsefi Rehberlik *name"
        )

        val randomSubject = subjectList.random()
        val randomTone = toneList.random()
        val randomTitle = titleList.random()

        val prompt = "$randomTone bir tavsiye ver. Konu: $randomSubject"

        return NotificationAI(
            prompt = prompt,
            title = randomTitle
        )
    }


}


data class NotificationAI(
    val prompt: String,
    val title: String
)


