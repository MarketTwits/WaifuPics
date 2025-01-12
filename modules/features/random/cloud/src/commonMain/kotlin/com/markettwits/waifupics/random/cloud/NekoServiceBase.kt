package com.markettwits.waifupics.random.cloud

import com.markettwits.waifupics.cloud.http.HttpClientProvider
import com.markettwits.waifupics.random.cloud.models.RandomImageItemCloud
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post


internal class NekoServiceBase(
    httpClient: HttpClientProvider
) : NekoService {

    private val json = httpClient.json()

    private val client = httpClient.provide(false)

    override suspend fun randomImage(ageRating: List<String>, limit: Int): List<RandomImageItemCloud> {
        val response = client.get("?url=${API_URL}/images/random") {
            url {
                parameters.append("rating", ageRating.joinToString(","))
                parameters.append("limit", limit.toString())
            }
        }
        return json.decodeFromString(response.body())
    }

    override suspend fun report(id: Int) {
        client.post("?url=${API_URL}/images/report") {
            parameter("id", id)
        }
    }

    companion object {
        const val API_URL = "https://api.nekosapi.com/v4"
        const val BASE_URL = "https://corsproxy.io/"
    }
}