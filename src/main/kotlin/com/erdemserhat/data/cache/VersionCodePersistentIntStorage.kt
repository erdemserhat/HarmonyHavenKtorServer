package com.erdemserhat.data.cache

import java.io.File

object PersistentVersionStorage {
    private val file = File("versionCodeStorage.txt")

    fun getVersionCode(): Int {
        return if (file.exists()) file.readText().toIntOrNull() ?: 39 else 39
    }

    fun setVersionCode(value: Int) {
        file.writeText(value.toString())
    }
}
