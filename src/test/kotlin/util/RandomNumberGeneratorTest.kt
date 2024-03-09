package util

import com.erdemserhat.util.RandomNumberGenerator.Companion.generateRandomAuthCode
import org.junit.Test
import kotlin.test.assertEquals

class RandomNumberGeneratorTest {

    @Test
    fun `randomNumberGenerator test`(){
        val code = generateRandomAuthCode(6)

        assertEquals(6,code.length)
        println(code)
    }
}