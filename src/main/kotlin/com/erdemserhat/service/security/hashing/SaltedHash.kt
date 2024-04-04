package com.erdemserhat.service.security.hashing

data class SaltedHash(
    val hash:String,
    val salt:String
)
