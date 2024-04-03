package com.erdemserhat.security.hashing

data class SaltedHash(
    val hash:String,
    val salt:String
)
