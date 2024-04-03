package com.erdemserhat.routes


data class User(
    val username: String,
    val password: String,
    val salt: String,
    var id:Int =1
){
    init {
        id++
    }
}