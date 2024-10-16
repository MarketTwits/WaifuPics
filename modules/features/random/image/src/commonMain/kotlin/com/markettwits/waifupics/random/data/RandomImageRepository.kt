package com.markettwits.waifupics.random.data

import com.markettwits.cache.image.ImageRepository
import com.markettwits.waifupics.random.cloud.NekoService
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.random.report.mapper.ReportedImageMapper
import com.markettwits.waifupics.random.report.model.ImageReportUi
import com.markettwits.waifupics.result.HandleNetworkResult

interface RandomImageRepository {

    suspend fun reportImage(id: Int): ImageReportUi

    suspend fun fetchRandomImage(filters: List<String>): RandomImageState

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
        private val reportedImageMapperCloud: ReportedImageMapper,
        private val handleNetwork: HandleNetworkResult,
        private val cache: ImageRepository
    ) : RandomImageRepository {

        override suspend fun reportImage(id: Int): ImageReportUi {
            val result = handleNetwork.tryRequest {
                service.report(id)
            }
            return result.map(reportedImageMapperCloud)
        }

        override suspend fun fetchRandomImage(filters: List<String>): RandomImageState {
            val result = handleNetwork.tryRequest {
                service.randomImage(filters).items.first()
            }
            return result.map(imageMapperCloud)
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