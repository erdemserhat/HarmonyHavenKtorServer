package com.erdemserhat

import com.erdemserhat.models.Quote
import com.erdemserhat.service.di.DatabaseModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object CentralCache {
    var allQuotes = listOf<Quote>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            do {
                allQuotes = DatabaseModule.quoteRepository.getQuotes()
                delay(10_000)
            } while (true)

        }
    }
}