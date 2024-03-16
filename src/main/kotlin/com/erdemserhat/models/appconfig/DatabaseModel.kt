package com.erdemserhat.models.appconfig

data class DatabaseModel(
    val host:String,
    val databaseName:String,
    val username:String,
    val password:String,
    val port:String,
    val useSSL:String
)
