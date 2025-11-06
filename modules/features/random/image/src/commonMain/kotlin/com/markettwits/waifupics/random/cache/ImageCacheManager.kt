package com.markettwits.waifupics.random.cache

import com.markettwits.waifupics.random.model.RandomImageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Менеджер для управления кешем изображений.
 * Хранит очередь предзагруженных изображений и управляет их загрузкой.
 */
interface ImageCacheManager {

    /**
     * Количество оставшихся изображений в кеше
     */
    fun remainingImagesCount(): StateFlow<Int>

    /**
     * Получить следующее изображение из кеша
     */
    fun getNextImage(): RandomImageState?

    /**
     * Добавить изображения в кеш
     */
    fun addImages(images: List<RandomImageState>)

    /**
     * Очистить кеш
     */
    fun clear()

    /**
     * Проверить, нужна ли подгрузка новых изображений
     */
    fun shouldLoadMore(): Boolean

    class Base(
        private val threshold: Int = 5,
        private val batchSize: Int = 10
    ) : ImageCacheManager {

        private val imageQueue = mutableListOf<RandomImageState>()
        private val _remainingCount = MutableStateFlow(0)

        override fun remainingImagesCount(): StateFlow<Int> = _remainingCount.asStateFlow()

        override fun getNextImage(): RandomImageState? {
            return if (imageQueue.isNotEmpty()) {
                val image = imageQueue.removeAt(0)
                _remainingCount.value = imageQueue.size
                image
            } else {
                null
            }
        }

        override fun addImages(images: List<RandomImageState>) {
            imageQueue.addAll(images)
            _remainingCount.value = imageQueue.size
        }

        override fun clear() {
            imageQueue.clear()
            _remainingCount.value = 0
        }

        override fun shouldLoadMore(): Boolean {
            return imageQueue.size <= threshold
        }
    }
}
