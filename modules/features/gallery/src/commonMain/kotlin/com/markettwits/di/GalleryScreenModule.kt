package com.markettwits.di


import com.markettwits.cache_datasource.FavoriteImageCacheToUiMapper
import com.markettwits.cache_datasource.GalleryRepository
import com.markettwits.cache_datasource.image.cacheDataSourceModule
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.core.wrappers.dispatchersList
import com.markettwits.image_action.api.ImageLoader
import com.markettwits.image_action.api.imageActionModule
import com.markettwits.presentation.copy.SystemService
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.list.GalleryViewModel
import com.markettwits.presentation.screens.list.communication.DetailCommunication
import com.markettwits.presentation.screens.list.communication.GalleryCommunication
import com.markettwits.presentation.screens.list.communication.SelectedImageCommunication
import com.markettwits.presentation.screens.list.communication.SelectedModeCommunication
import org.koin.dsl.module


val galleryModule = module {
    includes(imageActionModule, cacheDataSourceModule)
    val communication = DetailCommunication.Base()
    val galleryCommunication = GalleryCommunication.Base()
    single<ImageLoader> { get() }
    single<GalleryRepository>{
        GalleryRepository.Base(
            dataSource = get(),
            cacheToUiMapper = FavoriteImageCacheToUiMapper.Base(),
            imageLoader = get()
        )
    }
    single<GalleryViewModel> {
        GalleryViewModel.Base(
            gallery = galleryCommunication,
            repository = get(),
            async = AsyncViewModel.Base(RunAsync.Base(dispatchersList)),
            current = communication,
            selectedModeCommunication = SelectedModeCommunication.Base(),
            navigation = get(),
            selectedImageCommunication = SelectedImageCommunication.Base(),
            imageIntentAction = get()
        )
    }
    single<GalleryScreenViewModel> {
         GalleryScreenViewModel.Base(
            communication,
            galleryCommunication,
            AsyncViewModel.Base(RunAsync.Base(dispatchersList)),
            get(),
            get(),
            get(),
            object : SystemService {
                override fun copy(text: String) {

                }
            }
        )
    }
}