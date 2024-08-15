package com.erdemserhat.service.pwrservice

import com.erdemserhat.romote.mail.sendPasswordResetMail
import com.erdemserhat.dto.responses.RequestResult
import com.erdemserhat.util.RandomNumberGenerator.Companion.generateRandomAuthCode
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * Şifre sıfırlama işlevselliği için sorumlu hizmet.
 */
@OptIn(DelicateCoroutinesApi::class)
class PasswordResetService {

    private var code: String = ""

    /**
     * Verilen e-posta adresi için şifre sıfırlama talebi oluşturur.
     */
    fun createRequest(email: String): RequestResult {
        // Rastgele bir kimlik doğrulama kodu oluştur
        code = generateRandomAuthCode(6)

        // Havuzdan süresi dolmuş talepleri kaldır
        PasswordResetRequestsPool.removeExpiredRequests()

        // Bu e-posta ile zaten aktif bir talep olup olmadığını kontrol et
        val isAlreadyRequested = PasswordResetRequestsPool.isAlreadyRequested(email)
        if (isAlreadyRequested) {
            return RequestResult(
                false,
                "Bu e-posta ile zaten aktif bir talep var. Lütfen birkaç dakika bekleyin ve tekrar deneyin."
            )
        }

        // Şifre sıfırlama maili gönder
        sendPasswordResetMail(email, code)

        // Aday sıfırlama talebini havuza ekle
        PasswordResetRequestsPool.addCandidateResetRequest(CandidatePasswordResetRequest(code, email))

        return RequestResult(
            true,
            "$email adresine bir e-posta gönderildi."
        )
    }

    /**
     * Şifre sıfırlama talebini doğrular.
     */
    fun authenticateRequest(email: String, code: String): RequestResult {
        // Havuzdan sıfırlama talebini al
        val claim = PasswordResetRequestsPool.getRequestIfExist(email)

        // Bu e-posta ile bekleyen bir talep yoksa, hata döndür
        if (claim == null) {
            return RequestResult(
                false,
                "Bu e-posta ile bekleyen bir talep bulunmuyor. Lütfen yeni bir talep oluşturun."
            )
        }

        // Talep süresi dolmuşsa, hata döndür
        if (claim.isExpired) {
            return RequestResult(
                false,
                "Talebiniz süresi dolmuş. Lütfen kodu belirtilen süre içinde girin."
            )
        }

        // Kod geçersizse, deneme sayısını artır ve hata döndür
        if (claim.code != code) {
            claim.incrementAttempt()
            if (claim.attempts > 3) {
                PasswordResetRequestsPool.removeExpiredRequests()
                return RequestResult(
                    false,
                    "Yanlış kodu 3 kez girdiniz. Güvenlik nedenleriyle yeni bir talep oluşturun."
                )
            }
            return RequestResult(
                false,
                "Geçersiz kod."
            )
        }

        // Doğrulama başarılıysa, başarı döndür
        return RequestResult(true)
    }
}
