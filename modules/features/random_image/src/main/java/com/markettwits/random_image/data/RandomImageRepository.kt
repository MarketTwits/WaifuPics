package com.markettwits.random_image.data

import android.graphics.drawable.Drawable
import com.markettwits.data.ImageRepository
import com.markettwits.random_image.data.cloud.HandleNetworkResult
import com.markettwits.random_image.data.mapper.RandomImageMapperCloud
import com.markettwits.random_image.data.mapper.ReportedImageMapperCloud
import com.markettwits.random_image.data.network.NekoService
import com.markettwits.random_image.presentation.components.report_image.ImageReportUi
import com.markettwits.random_image.presentation.screen.RandomImageUiState

interface RandomImageRepository {
    suspend fun reportImage(id : Int) : ImageReportUi
    suspend fun fetchRandomImage(filters: List<String>): RandomImageUiState
    suspend fun addToFavorite(image : Drawable, url : String,protected : Boolean)
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

        override suspend fun fetchRandomImage(filters: List<String>): RandomImageUiState {
            val result = async.tryRequest {
                service.randomImage(filters).items[0]
            }
            return result.map(imageMapperCloud)
        }

        override suspend fun addToFavorite(image: Drawable, url: String, protected: Boolean) {
            cache.addOrDelete(image, url, protected)
        }
    }
}