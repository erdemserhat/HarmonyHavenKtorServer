package com.erdemserhat.models

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id:Int,
    val title:String,
    val slug:String = title.toSlug(),
    val content:String,
    val contentPreview:String,
    val publishDate:String,
    val categoryId:Int,
    val imagePath:String

)

fun String.toSlug(): String {
    return this
        .lowercase()
        .replace(Regex("ç"), "c")
        .replace(Regex("ğ"), "g")
        .replace(Regex("ı"), "i")
        .replace(Regex("İ"), "i") // Büyük 'İ' karakterini küçük 'i' olarak değiştiriyoruz
        .replace(Regex("ö"), "o")
        .replace(Regex("ş"), "s")
        .replace(Regex("ü"), "u")
        .replace(Regex("[^a-z0-9\\s-]"), "") // Türkçe karakterler dışındaki özel karakterleri kaldır
        .replace(Regex("\\s+"), "-")         // Boşlukları tire ile değiştir
        .replace(Regex("-+"), "-")           // Birden fazla tireyi tekle
        .trim('-')                           // Baş ve sondaki tireleri kaldır
}