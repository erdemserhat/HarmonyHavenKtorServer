package com.erdemserhat.models.appconfig

data class SMTPModel(
    val host:String,
    val port:String,
    val username:String,
    val password:String,
    val smtpAuthEnabled:String,
    val starttlsEnabled:String
)