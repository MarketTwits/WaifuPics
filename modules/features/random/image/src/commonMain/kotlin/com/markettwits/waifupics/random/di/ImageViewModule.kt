package com.markettwits.waifupics.random.di

import com.markettwits.async.AsyncViewModel
import com.markettwits.async.RunAsync
import com.markettwits.async.dispatchersList
import com.markettwits.cache.image.cacheDataSourceModule
import com.markettwits.image_action.api.imageActionModule
import com.markettwits.waifupics.filter.domain.FilterDataSource
import com.markettwits.waifupics.random.cloud.cloudDataSourceModule
import com.markettwits.waifupics.random.data.RandomImageMapperCloud
import com.markettwits.waifupics.random.data.RandomImageRepository
import com.markettwits.waifupics.random.report.mapper.ReportedImageMapper
import com.markettwits.waifupics.random.viewmodel.ImageViewModel
import com.markettwits.waifupics.random.viewmodel.LoadedImageCommunication
import com.markettwits.waifupics.random.viewmodel.RandomImageCommunication
import com.markettwits.waifupics.result.HandleNetworkResult
import com.markettwits.waifupics.result.NetworkExceptionMapper
import org.koin.dsl.module

val randomImageModule = module {
    includes(cloudDataSourceModule, cacheDataSourceModule,imageActionModule)
    val filterCommunication = com.markettwits.waifupics.filter.viewmodel.FilterCommunication.Base()
    single<com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterCommunication> {
        com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterCommunication.Base()
    }
    single<ImageViewModel> {
        ImageViewModel.Base(
            filterResult = filterCommunication,
            protectedMapper = com.markettwits.waifupics.filter.domain.ProtectedMapper.Base(),
            async = AsyncViewModel.Base(RunAsync.Base(dispatchersList)),
            randomImageCommunication = RandomImageCommunication.Base(),
            loadedImageCommunication = LoadedImageCommunication.Base(),
            repository = RandomImageRepository.Base(
                service = get(),
                imageMapperCloud = RandomImageMapperCloud(),
                reportedImageMapperCloud = ReportedImageMapper(),
                handleNetwork = HandleNetworkResult.Base(
                    NetworkExceptionMapper()
                ),
                cache = get(),
            ),
            shareImageAction = get()
        )
    }
    single<com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterViewModel> {
        com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterViewModel(
            filterChecked = com.markettwits.waifupics.filter.domain.FilterChecked.Base(),
            communication = com.markettwits.waifupics.filter.viewmodel.AgeRatingFilterCommunication.Base(),
            filterCommunication = filterCommunication,
            filterDataSource = FilterDataSource.Base()
        )
    }
}