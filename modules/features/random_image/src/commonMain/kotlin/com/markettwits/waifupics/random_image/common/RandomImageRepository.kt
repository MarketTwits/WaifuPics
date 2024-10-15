package com.markettwits.waifupics.random_image.common

import com.markettwits.cache.image.ImageRepository
import com.markettwits.cloud_datasource.data.cloud.HandleNetworkResult
import com.markettwits.cloud_datasource.data.network.NekoService
import com.markettwits.waifupics.random_image.image.mapper.RandomImageMapperCloud
import com.markettwits.waifupics.random_image.image.model.RandomImageState
import com.markettwits.waifupics.random_image.report.components.ImageReportUi
import com.markettwits.waifupics.random_image.report.mapper.ReportedImageMapperCloud

interface RandomImageRepository {

    suspend fun reportImage(id : Int) : ImageReportUi

    suspend fun fetchRandomImage(filters: List<String>): RandomImageState

    suspend fun addToFavorite(
        id : Int,
        width : Int,
        height : Int,
        url : String,
        isProtected : Boolean
    )

    class Base(
        private val service: NekoService,
        private val imageMapperCloud: RandomImageMapperCloud,
        private val reportedImageMapperCloud : ReportedImageMapperCloud,
        private val async : HandleNetworkResult,
        private val cache : ImageRepository
    ) : RandomImageRepository {

        override suspend fun reportImage(id: Int): ImageReportUi {
            val result = async.tryRequest {
                service.report(id)
            }
            return result.map(reportedImageMapperCloud)
        }

        override suspend fun fetchRandomImage(filters: List<String>): RandomImageState {
            val result = async.tryRequest {
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