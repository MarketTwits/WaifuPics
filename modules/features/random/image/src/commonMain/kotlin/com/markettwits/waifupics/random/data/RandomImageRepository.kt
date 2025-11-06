package com.markettwits.waifupics.random.data

import com.markettwits.cache.image.ImageCacheRepository
import com.markettwits.waifupics.random.cloud.NekoService
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.result.HandleNetworkResult

interface RandomImageRepository {

    suspend fun fetchRandomImage(filters: List<String>): RandomImageState

    suspend fun fetchRandomImages(filters: List<String>, limit: Int): List<RandomImageState>

    suspend fun addToFavorite(
        id: Int,
        width: Int,
        height: Int,
        url: String,
        isProtected: Boolean
    )

    class Base(
        private val service: NekoService,
        private val imageMapperCloud: RandomImageMapperCloud,
        private val handleNetwork: HandleNetworkResult,
        private val cache: ImageCacheRepository
    ) : RandomImageRepository {

        override suspend fun fetchRandomImage(filters: List<String>): RandomImageState {
            val result = handleNetwork.tryRequest {
                service.randomImage(filters).first()
            }
            return result.map(imageMapperCloud)
        }

        override suspend fun fetchRandomImages(filters: List<String>, limit: Int): List<RandomImageState> {
            return try {
                // Один запрос с нужным limit вместо множества запросов
                val cloudImages = service.randomImage(filters, limit)

                // Мапим все полученные изображения
                cloudImages.mapNotNull { cloudImage ->
                    try {
                        val result = handleNetwork.tryRequest { cloudImage }
                        val state = result.map(imageMapperCloud)
                        if (state is RandomImageState.Success) state else null
                    } catch (e: Exception) {
                        null // Пропускаем изображения с ошибками
                    }
                }
            } catch (e: Exception) {
                emptyList() // Возвращаем пустой список в случае ошибки сети
            }
        }

        override suspend fun addToFavorite(
            id: Int,
            width: Int,
            height: Int,
            url: String,
            isProtected: Boolean
        ) {
            cache.addOrDelete(id, width, height, url, isProtected)
        }
    }
}