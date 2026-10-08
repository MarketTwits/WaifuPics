package com.markettwits.waifupics.random

import com.markettwits.waifupics.random.cloud.NekoService
import com.markettwits.waifupics.random.cloud.models.RandomImageItemCloud
import com.markettwits.waifupics.random.data.RandomImageMapperCloud
import com.markettwits.waifupics.random.data.RandomImageRepository
import com.markettwits.waifupics.result.HandleNetworkResult
import com.markettwits.waifupics.result.NetworkExceptionMapper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class RandomImageRepositoryTest {
    private val handler = HandleNetworkResult.Base(NetworkExceptionMapper())

    private fun repository(error: Exception) = RandomImageRepository.Base(
        object : NekoService {
            override suspend fun randomImage(ageRating: List<String>, limit: Int): List<RandomImageItemCloud> = throw error
            override suspend fun report(id: Int) = Unit
        }, RandomImageMapperCloud(), handler, FakeCache()
    )

    @Test
    fun batchNetworkErrorIsNotConvertedToEmptyList() = runTest {
        val error = IllegalStateException("server failed")
        assertSame(error, assertFailsWith<IllegalStateException> {
            repository(error).fetchRandomImages(listOf("safe"), 10)
        })
    }

    @Test
    fun batchCancellationPropagates() = runTest {
        val error = CancellationException("filter changed")
        assertSame(error, assertFailsWith<CancellationException> {
            repository(error).fetchRandomImages(listOf("safe"), 10)
        })
    }

    @Test
    fun singleRequestHandlerAlsoPropagatesCancellation() = runTest {
        val error = CancellationException("screen closed")
        assertSame(error, assertFailsWith<CancellationException> {
            handler.tryRequest<String> { throw error }
        })
    }
}
