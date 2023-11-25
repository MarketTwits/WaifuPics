package com.markettwits.random_image.di

import com.markettwits.core.Core
import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.core.di.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.ImageRepository
import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImageLoaderDataSource
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.image_action.ImageFileWrapper
import com.markettwits.image_action.impl.ImageIntentActionImpl
import com.markettwits.random_image.data.RandomImageRepository
import com.markettwits.random_image.data.ReportedImageMapperCloud
import com.markettwits.random_image.data.cloud.ExceptionMapper
import com.markettwits.random_image.data.cloud.HandleNetworkResult
import com.markettwits.random_image.data.cloud.OkkHttpWrapper
import com.markettwits.random_image.data.cloud.RetrofitFactory
import com.markettwits.random_image.data.net.NekoService
import com.markettwits.random_image.data.net.RandomImageMapperCloud
import com.markettwits.random_image.presentation.features.filter.ProtectedMapper
import com.markettwits.random_image.presentation.features.filter.presentation.FilterCommunication
import com.markettwits.random_image.presentation.random_image_screen.ImageViewModel
import com.markettwits.random_image.presentation.random_image_screen.LoadedImageCommunication
import com.markettwits.random_image.presentation.random_image_screen.RandomImageCommunication
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class ImageViewModule(
    private val core: Core,
    private val filter: FilterCommunication
) : Module<ImageViewModel.Base> {
    //FIXME
    private val repository =
        ImageRepository.Base(
            ImagesCacheDataSource(RealmDatabaseProvider.Base()),
            ImageUiToCacheMapper.Base(),
            ImageLoaderDataSource.Base(core.context(),DispatchersList.Base())
        )

    override fun viewModel() =
        ImageViewModel.Base(
            filter,
            ProtectedMapper.Base(),
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            RandomImageCommunication.Base(),
            LoadedImageCommunication.Base(),
            RandomImageRepository.Base(
                RetrofitFactory.Base(NekoService.BASE_URL, provideClient(), provideJson()).create(NekoService::class.java),
                RandomImageMapperCloud(),
                ReportedImageMapperCloud(),
                HandleNetworkResult.Base(ExceptionMapper()),
                repository,
            ),
            ImageIntentActionImpl(core.context(), ImageFileWrapper.Base(core.context()))
        )
    private fun provideClient(): OkHttpClient {
        return OkkHttpWrapper.Base().client()
    }
    private fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }
}