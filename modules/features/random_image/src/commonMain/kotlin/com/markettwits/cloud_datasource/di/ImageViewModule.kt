package com.markettwits.cloud_datasource.di

import com.markettwits.cloud_datasource.data.cloud.HandleNetworkResult
import com.markettwits.cloud_datasource.data.cloud.exceptions.ExceptionMapper
import com.markettwits.cloud_datasource.data.mapper.RandomImageMapperCloud
import com.markettwits.cloud_datasource.data.mapper.ReportedImageMapperCloud
import com.markettwits.cloud_datasource.domain.FilterChecked
import com.markettwits.cloud_datasource.domain.RandomImageRepository
import com.markettwits.cloud_datasource.presentation.components.filter.ProtectedMapper
import com.markettwits.cloud_datasource.presentation.components.filter.data.FilterDataSource
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.FilterCommunication
import com.markettwits.cloud_datasource.presentation.screen.ImageViewModel
import com.markettwits.cloud_datasource.presentation.screen.LoadedImageCommunication
import com.markettwits.cloud_datasource.presentation.screen.RandomImageCommunication
import com.markettwits.core.di.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.di.CacheDataSourceModule
import com.markettwits.di.cacheDataSourceModule
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.image_action.api.imageActionModule
import org.koin.dsl.module

class ImageViewModule(
    private val filter: FilterCommunication
) : Module<ImageViewModel.Base> {

    override fun viewModel() =
        ImageViewModel.Base(
            filterResult = filter,
            protectedMapper = ProtectedMapper.Base(),
            async = AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            randomImageCommunication = RandomImageCommunication.Base(),
            loadedImageCommunication = LoadedImageCommunication.Base(),
            repository = RandomImageRepository.Base(
                service = NekoServiceCloudModule.provideNekoService(),
                imageMapperCloud = RandomImageMapperCloud(),
                reportedImageMapperCloud = ReportedImageMapperCloud(),
                async = HandleNetworkResult.Base(ExceptionMapper()),
                cache = CacheDataSourceModule.provideImageRepository(),
            ),
            shareImageAction = object : ImageIntentAction.ShareImage {
                override suspend fun shareImage(imagePath: List<String>) {

                }

                override suspend fun shareImage(imagePath: String) {

                }
            }
        )
}
val randomImageModule = module {
    includes(cloudDataSourceModule, cacheDataSourceModule,imageActionModule)
    val filterCommunication = FilterCommunication.Base()
    single<AgeRatingFilterCommunication> {
        AgeRatingFilterCommunication.Base()
    }
    single<ImageViewModel> {
        ImageViewModel.Base(
            filterResult = filterCommunication,
            protectedMapper = ProtectedMapper.Base(),
            async = AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            randomImageCommunication = RandomImageCommunication.Base(),
            loadedImageCommunication = LoadedImageCommunication.Base(),
            repository = RandomImageRepository.Base(
                service = NekoServiceCloudModule.provideNekoService(),
                imageMapperCloud = RandomImageMapperCloud(),
                reportedImageMapperCloud = ReportedImageMapperCloud(),
                async = HandleNetworkResult.Base(ExceptionMapper()),
                cache = CacheDataSourceModule.provideImageRepository(),
            ),
            shareImageAction = get()
        )
    }
    single<AgeRatingFilterViewModel> {
        AgeRatingFilterViewModel(
            filterChecked = FilterChecked.Base(),
            communication =  AgeRatingFilterCommunication.Base(),
            filterCommunication =  filterCommunication,
            defaultValue =  FilterDataSource.Base()
        )
    }
}