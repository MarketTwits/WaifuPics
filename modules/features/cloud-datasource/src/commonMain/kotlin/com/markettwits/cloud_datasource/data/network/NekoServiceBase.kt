package com.markettwits.cloud_datasource.data.network

import com.markettwits.cloud_datasource.data.cloud.base.HttpClientProvider
import com.markettwits.cloud_datasource.data.network.models.RandomImageCloud
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType


internal class NekoServiceBase(
    httpClient: HttpClientProvider
) : NekoService {

    private val json = httpClient.json()

    private val client = httpClient.provide(false)

    override suspend fun randomImage(ageRating: List<String>, limit: Int): RandomImageCloud {
        val response = client.get {
            contentType(ContentType.Application.Json)
            url("images/random")
            ageRating.forEach { rating ->
                parameter("rating", rating)
            }
            parameter("limit", limit)
        }
        return json.decodeFromString(response.body())
    }

    override suspend fun report(id: Int) {
        val response = client.post("images/$id/report") {
            contentType(ContentType.Application.Json)
        }
        return json.decodeFromString(response.body())
    }

    companion object {
        const val BASE_URL = "https://api.nekosapi.com/v3/"
    }

}