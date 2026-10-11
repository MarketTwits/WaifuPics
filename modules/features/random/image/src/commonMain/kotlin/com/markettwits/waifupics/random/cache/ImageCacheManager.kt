package com.markettwits.waifupics.random.cache

import com.markettwits.waifupics.random.model.RandomImageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages the image cache.
 * Keeps a queue of prefetched images and controls when to load more.
 */
interface ImageCacheManager {

    /**
     * Number of images remaining in the cache.
     */
    fun remainingImagesCount(): StateFlow<Int>

    /**
     * Get the next cached image.
     */
    fun getNextImage(): RandomImageState?

    /**
     * Add images to the cache.
     */
    fun addImages(images: List<RandomImageState>)

    /**
     * Clear the cache.
     */
    fun clear()

    /**
     * Check whether more images should be loaded.
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
