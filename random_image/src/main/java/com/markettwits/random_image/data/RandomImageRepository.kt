package com.markettwits.random_image.data

import android.graphics.drawable.Drawable
import com.markettwits.data.ImageRepository
import com.markettwits.random_image.presentation.RandomImageUiState
import com.markettwits.random_image.presentation.report_image.ImageReportUi
import com.markettwits.waifupics.view.main.data.net.MakeService

interface RandomImageRepository {
    suspend fun reportImage(id : Int) : ImageReportUi
    suspend fun fetchRandomImage(filters: List<String>): RandomImageUiState
    suspend fun addToFavorite(image : Drawable, url : String,protected : Boolean)
    class Base(
        private val service: MakeService,
        private val mapper: RandomImageUiMapper,
        private val cache : ImageRepository
    ) : RandomImageRepository {
        override suspend fun reportImage(id: Int): ImageReportUi {
            return try {
                service.service().report(id)
                ImageReportUi("Sended")
            }catch (e : Exception){
                ImageReportUi("Can't send request : ${e.message}")
            }
        }

        override suspend fun fetchRandomImage(filters: List<String>): RandomImageUiState {
            return try {
                val request = service.service().randomImage(filters)
                val image = mapper.map(request.items[0])
                image
            } catch (e: Exception) {
                RandomImageUiState.Error(e.message.toString())
            }
        }

        override suspend fun addToFavorite(image: Drawable, url: String, protected: Boolean) {
            cache.addOrDelete(image, url, protected)
        }
    }
}