package com.erdemserhat.security

import java.security.MessageDigest

fun hashPassword(password: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

fun verifyPasswordWithHash(password: String, hashedPassword: String): Boolean {
    return hashPassword(password) == hashedPassword
}