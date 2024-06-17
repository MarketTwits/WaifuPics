package com.markettwits.random_image.di

import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.core.di.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.ImageRepository
import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.random_image.data.RandomImageRepository
import com.markettwits.random_image.data.cloud.HandleNetworkResult
import com.markettwits.random_image.data.cloud.RetrofitFactory
import com.markettwits.random_image.data.cloud.exceptions.ExceptionMapper
import com.markettwits.random_image.data.mapper.RandomImageMapperCloud
import com.markettwits.random_image.data.mapper.ReportedImageMapperCloud
import com.markettwits.random_image.data.network.NekoService
import com.markettwits.random_image.presentation.components.filter.ProtectedMapper
import com.markettwits.random_image.presentation.components.filter.presentation.FilterCommunication
import com.markettwits.random_image.presentation.screen.ImageViewModel
import com.markettwits.random_image.presentation.screen.LoadedImageCommunication
import com.markettwits.random_image.presentation.screen.RandomImageCommunication
import kotlinx.serialization.json.Json

class ImageViewModule(
    private val filter: FilterCommunication
) : Module<ImageViewModel.Base> {
    //FIXME
    private val repository =
        ImageRepository.Base(
            database =  ImagesCacheDataSource(RealmDatabaseProvider.Base()),
            uiToCache =  ImageUiToCacheMapper.Base(),
        )

    override fun viewModel() =
        ImageViewModel.Base(
            filter,
            ProtectedMapper.Base(),
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            RandomImageCommunication.Base(),
            LoadedImageCommunication.Base(),
            RandomImageRepository.Base(
                service = RetrofitFactory.Base(
                    baseUrl = NekoService.BASE_URL,
                    json = provideJson(),
                ).create<NekoService>(),
                RandomImageMapperCloud(),
                ReportedImageMapperCloud(),
                HandleNetworkResult.Base(ExceptionMapper()),
                repository,
            ),
            shareImageAction = object :ImageIntentAction.ShareImage{
                override suspend fun shareImage(imagePath: List<String>) {

                }

                override suspend fun shareImage(imagePath: String) {

                }
            }
            //ImageIntentActionImpl(core.context(), ImageFileWrapper.Base(core.context()))
        )
    private fun provideJson(): Json {
        return Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
}