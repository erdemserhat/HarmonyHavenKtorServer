package com.erdemserhat.plugins

import com.erdemserhat.domain.email.EmailConfig
import com.erdemserhat.models.appconfig.SMTPModel
import io.ktor.server.application.*

fun Application.configureSMTP(){
    val smtpHost = environment.config.property("harmonyhaven.email.smtpHost").getString()
    val smtpPort = environment.config.property("harmonyhaven.email.smtpPort").getString()
    val username = environment.config.property("harmonyhaven.email.username").getString()
    val password = environment.config.property("harmonyhaven.email.password").getString()
    val smtpAuthEnabled =environment.config.property("harmonyhaven.email.smtpAuthEnabled").getString()
    val starttlsEnabled = environment.config.property("harmonyhaven.email.startTLSEnabled").getString()

    val smtpModel = SMTPModel(
        host = smtpHost,
        port = smtpPort,
        username = username,
        password = password,
        starttlsEnabled = starttlsEnabled,
        smtpAuthEnabled = smtpAuthEnabled

    )

    EmailConfig.init(smtpModel)

}