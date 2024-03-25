package com.erdemserhat

import com.erdemserhat.di.DatabaseModule.articleRepository

import com.erdemserhat.models.Article

import com.erdemserhat.plugins.*

import io.ktor.server.application.*
import java.io.File
import java.io.FileOutputStream


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
    configureRemoteDatabase()
    configureFirebase()

    // use this area for testing.............


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




