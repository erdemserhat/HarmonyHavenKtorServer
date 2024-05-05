

import com.erdemserhat.models.QuoteCategory
import com.erdemserhat.service.di.DatabaseModule
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.test.*

class QuoteCategoryRouteTest {
    @OptIn(InternalAPI::class)
    @Test
    fun addQuoteCategoryV1Test() = testApplication {
        val quoteCategory = QuoteCategory(0, "Example Category")

        val jsonBody = Json.encodeToString(quoteCategory)

        val response = client.post("/api/v1/add-quote-category") {
            contentType(ContentType.Application.Json)
            body = jsonBody
        }
        assertEquals(HttpStatusCode.OK, response.status)
        val result = DatabaseModule.quoteCategoryRepository.getCategories().any{it.name=="Example Category"}

        assertEquals(true,result)
    }

    @OptIn(InternalAPI::class)
    @Test
    fun deleteQuoteCategoryV1Test() = testApplication {
        val quoteCategory = QuoteCategory(0,"Delete Test Category")
        val jsonBody = Json.encodeToString(quoteCategory)

        client.post("/api/v1/add-quote-category"){
            contentType(ContentType.Application.Json)
            body = jsonBody
        }

        val response = client.delete("/api/v1/delete-quote-category/${quoteCategory.id}")
        assertEquals(HttpStatusCode.OK,response.status)


    }


}
