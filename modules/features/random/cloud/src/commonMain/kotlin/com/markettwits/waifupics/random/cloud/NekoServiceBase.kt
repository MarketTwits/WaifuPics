package com.markettwits.waifupics.random.cloud

import com.markettwits.waifupics.cloud.http.HttpClientProvider
import com.markettwits.waifupics.random.cloud.models.RandomImageItemCloud
import io.ktor.client.call.*
import io.ktor.client.request.*


internal class NekoServiceBase(
    httpClient: HttpClientProvider
) : NekoService {

    private val json = httpClient.json()

    private val client = httpClient.provide(false)

    override suspend fun randomImage(ageRating: List<String>, limit: Int): List<RandomImageItemCloud> {
        val response = client.get(Endpoints.RANDOM_IMAGE) {
            url {
                parameters.append(Params.RATING, ageRating.joinToString(","))
                parameters.append(Params.LIMIT, limit.toString())
            }
        }
        return json.decodeFromString(response.body())
    }

    override suspend fun report(id: Int) {
        client.post(Endpoints.REPORT_IMAGE) {
            parameter(Params.ID, id)
        }
    }

    companion object {
        val API_URL: String get() = nekoApiBaseUrl

        private object Endpoints {
            const val RANDOM_IMAGE = "images/random"
            const val REPORT_IMAGE = "images/report"
        }

        private object Params {
            const val RATING = "rating"
            const val LIMIT = "limit"
            const val ID = "id"
        }
    }
}
