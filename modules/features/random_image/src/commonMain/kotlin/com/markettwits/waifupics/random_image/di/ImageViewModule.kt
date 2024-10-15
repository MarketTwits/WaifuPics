package com.markettwits.waifupics.random_image.di

import com.markettwits.cache.image.cacheDataSourceModule
import com.markettwits.cloud_datasource.data.cloud.HandleNetworkResult
import com.markettwits.cloud_datasource.data.cloud.exceptions.ExceptionMapper
import com.markettwits.cloud_datasource.di.NekoServiceCloudModule
import com.markettwits.cloud_datasource.di.cloudDataSourceModule
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.ImageViewModel
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.LoadedImageCommunication
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.RandomImageCommunication
import com.markettwits.async.wrappers.AsyncViewModel
import com.markettwits.async.wrappers.RunAsync
import com.markettwits.async.wrappers.dispatchersList
import com.markettwits.image_action.api.imageActionModule
import com.markettwits.waifupics.random_image.image.mapper.RandomImageMapperCloud
import com.markettwits.waifupics.random_image.report.mapper.ReportedImageMapperCloud
import com.markettwits.waifupics.random_image.common.RandomImageRepository
import com.markettwits.waifupics.random_image.filter.domain.FilterChecked
import com.markettwits.waifupics.random_image.filter.domain.FilterDataSource
import com.markettwits.waifupics.random_image.filter.domain.ProtectedMapper
import com.markettwits.waifupics.random_image.filter.viewmodel.AgeRatingFilterCommunication
import com.markettwits.waifupics.random_image.filter.viewmodel.AgeRatingFilterViewModel
import com.markettwits.waifupics.random_image.filter.viewmodel.FilterCommunication
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