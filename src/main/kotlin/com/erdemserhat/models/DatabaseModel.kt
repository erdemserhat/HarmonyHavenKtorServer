package com.erdemserhat.models

data class DatabaseModel(
    val host:String,
    val databaseName:String,
    val username:String,
    val password:String,
    val port:String,
    val useSSL:String
)
