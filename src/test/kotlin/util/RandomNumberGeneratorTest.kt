package util

import com.erdemserhat.util.RandomNumberGenerator.Companion.generateRandomAuthCode
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class RandomNumberGeneratorTest {

    @Test
    fun `randomNumberGenerator test`(){
        val code = generateRandomAuthCode(6)

        assertEquals(6,code.length)
        println(code)
    }

    @Test
    fun `randomNumberGefnerator test`(){
        val uuid = "e84f0e4b-9689-4f08-a54a-a61aff36d66c"

        val UUID = UUID.fromString(uuid)
        println(UUID.version())
    }

    @Test
    fun containsMaxSequential() {
        val password: String ="Erdem.3451."
        val maxSequential: Int = 3
        if (password.length < maxSequential) {
            println("false")
        }

        val sequentialChars = "abcdefghijklmnopqrstuvwxyz0123456789"

        for (i in 0 until password.length - maxSequential + 1) {
            val subPassword = password.substring(i, i + maxSequential).toLowerCase()
            var count = 0
            for (j in 0 until subPassword.length - 1) {
                if (sequentialChars.indexOf(subPassword[j]) + 1 == sequentialChars.indexOf(subPassword[j + 1])) {
                    count++
                    if (count == maxSequential - 1) {
                        println("true")
                    }
                } else {
                    break
                }
            }
        }

        println("false")
    }



}