package com.markettwits.cloud_datasource.di

import com.markettwits.cache_datasource.image.cacheDataSourceModule
import com.markettwits.cloud_datasource.data.RandomImageMapperCloud
import com.markettwits.cloud_datasource.data.ReportedImageMapperCloud
import com.markettwits.cloud_datasource.data.cloud.HandleNetworkResult
import com.markettwits.cloud_datasource.data.cloud.exceptions.ExceptionMapper
import com.markettwits.cloud_datasource.domain.RandomImageRepository
import com.markettwits.cloud_datasource.presentation.filter.domain.FilterChecked
import com.markettwits.cloud_datasource.presentation.filter.domain.FilterDataSource
import com.markettwits.cloud_datasource.presentation.filter.domain.ProtectedMapper
import com.markettwits.cloud_datasource.presentation.filter.presentation.viewmodel.AgeRatingFilterCommunication
import com.markettwits.cloud_datasource.presentation.filter.presentation.viewmodel.AgeRatingFilterViewModel
import com.markettwits.cloud_datasource.presentation.filter.presentation.viewmodel.FilterCommunication
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.ImageViewModel
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.LoadedImageCommunication
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.RandomImageCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.core.wrappers.dispatchersList
import com.markettwits.image_action.api.imageActionModule
import org.koin.dsl.module

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
            async = AsyncViewModel.Base(RunAsync.Base(dispatchersList)),
            randomImageCommunication = RandomImageCommunication.Base(),
            loadedImageCommunication = LoadedImageCommunication.Base(),
            repository = RandomImageRepository.Base(
                service = NekoServiceCloudModule.provideNekoService(),
                imageMapperCloud = RandomImageMapperCloud(),
                reportedImageMapperCloud = ReportedImageMapperCloud(),
                async = HandleNetworkResult.Base(ExceptionMapper()),
                cache = get(),
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