package com.markettwits.waifupics.gallery.di


import com.markettwits.cache.image.cacheDataSourceModule
import com.markettwits.async.wrappers.AsyncViewModel
import com.markettwits.async.wrappers.RunAsync
import com.markettwits.async.wrappers.dispatchersList
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.image_action.api.ImageLoader
import com.markettwits.image_action.api.imageActionModule
import com.markettwits.waifupics.gallery.cache_datasource.GalleryRepository
import com.markettwits.waifupics.gallery.presentation.copy.SystemService
import com.markettwits.waifupics.gallery.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.waifupics.gallery.presentation.screens.list.GalleryViewModel
import com.markettwits.waifupics.gallery.presentation.screens.list.communication.DetailCommunication
import com.markettwits.waifupics.gallery.presentation.screens.list.communication.GalleryCommunication
import com.markettwits.waifupics.gallery.presentation.screens.list.communication.SelectedImageCommunication
import com.markettwits.waifupics.gallery.presentation.screens.list.communication.SelectedModeCommunication
import org.koin.dsl.module


val galleryModule = module {
    includes(imageActionModule, cacheDataSourceModule)
    val communication = DetailCommunication.Base()
    val galleryCommunication = GalleryCommunication.Base()
    single<ImageLoader> { get() }
    single<com.markettwits.waifupics.gallery.cache_datasource.GalleryRepository>{
        com.markettwits.waifupics.gallery.cache_datasource.GalleryRepository.Base(
            dataSource = get(),
            cacheToUiMapper = com.markettwits.waifupics.gallery.cache_datasource.FavoriteImageCacheToUiMapper.Base(),
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
            selectedImageCommunication = SelectedImageCommunication.Base(),
            imageIntentAction = get()
        )
    }
    single<GalleryScreenViewModel> {
         GalleryScreenViewModel.Base(
            item = communication,
            list = galleryCommunication,
            async = AsyncViewModel.Base(RunAsync.Base(dispatchersList)),
            repository = get<GalleryRepository>(),
            imageIntentAction = get<ImageIntentAction.Mutable>(),
            systemService = object : SystemService {
                override fun copy(text: String) {

                }
            }
        )
    }
}