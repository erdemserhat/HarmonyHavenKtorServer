package com.erdemserhat

import com.erdemserhat.database.FTPConfig.uploadFileToFtp
import com.erdemserhat.di.DatabaseModule
import com.erdemserhat.di.DatabaseModule.articleRepository
import com.erdemserhat.di.DatabaseModule.categoryRepository
import com.erdemserhat.models.Article
import com.erdemserhat.models.Category
import com.erdemserhat.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicInteger


fun main(args: Array<String>) {
    //Class.forName("com.mysql.jdbc.Driver")
    io.ktor.server.netty.EngineMain.main(args)




}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureTemplating()
    configureSMTP()
    configureFTP()

    // use this area for testing.............

   configureRemoteDatabase()
    val exArticleModel = Article(1, "title", "content", "2024-03-20", 1, "//12")

    val result = articleRepository.getAllArticles()
    println(result)


}




fun createSampleFile(): File {
    val fileContent = "Bu örnek dosyanın içeriğidir."
    val file = File("sample.txt")
    FileOutputStream(file).use { stream ->
        stream.write(fileContent.toByteArray())
    }
    return file
}



