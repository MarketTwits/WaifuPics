package com.markettwits.waifupics.gallery.di


import com.markettwits.async.wrappers.AsyncViewModel
import com.markettwits.async.wrappers.RunAsync
import com.markettwits.async.wrappers.dispatchersList
import com.markettwits.cache.image.cacheDataSourceModule
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.image_action.api.ImageLoader
import com.markettwits.image_action.api.imageActionModule
import com.markettwits.waifupics.gallery.common.FavoriteImageCacheToUiMapper
import com.markettwits.waifupics.gallery.common.GalleryRepository
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel
import com.markettwits.waifupics.gallery.items.components.communication.DetailCommunication
import com.markettwits.waifupics.gallery.items.components.communication.GalleryCommunication
import com.markettwits.waifupics.gallery.items.components.communication.GalleryScreenLabelsCommunication
import com.markettwits.waifupics.gallery.items.components.communication.SelectedImageCommunication
import com.markettwits.waifupics.gallery.items.components.communication.SelectedModeCommunication
import com.markettwits.waifupics.gallery.items.components.copy.SystemService
import com.markettwits.waifupics.gallery.items.viewmodel.GalleryViewModel
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
             labelsCommunication = GalleryScreenLabelsCommunication.Base(),
            systemService = object : SystemService {
                override fun copy(text: String) {

                }
            }
        )
    }
}