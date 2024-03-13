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


}