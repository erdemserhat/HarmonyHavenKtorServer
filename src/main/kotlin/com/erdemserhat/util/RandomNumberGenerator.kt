package com.erdemserhat.util

import kotlin.random.Random

class RandomNumberGenerator {
    companion object {
        fun generateRandomAuthCode(length: Int): String {
            val numbers = "1234567890"
            val sb = StringBuilder(length)
            repeat(length) {
                val randomIndex = Random.nextInt(numbers.length)
                sb.append(numbers[randomIndex])
            }

            return sb.toString()
        }
    }
}
