package com.markettwits.di

import com.markettwits.core.di.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.FavoriteImageCacheToUiMapper
import com.markettwits.data.GalleryRepository
import com.markettwits.image_action.api.EmptyIntentAction
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.image_action.api.ImageLoader
import com.markettwits.image_action.api.imageActionModule
import com.markettwits.presentation.copy.SystemService
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.list.GalleryViewModel
import com.markettwits.presentation.screens.list.communication.DetailCommunication
import com.markettwits.presentation.screens.list.communication.GalleryCommunication
import com.markettwits.presentation.screens.list.communication.SelectedImageCommunication
import com.markettwits.presentation.screens.list.communication.SelectedModeCommunication
import org.koin.dsl.module

class GalleryScreenModule(
    private val communication: DetailCommunication,
    private val repository: GalleryRepository,
    private val galleryCommunication: GalleryCommunication,
    private val navigation: GalleryRouter,
    private val imageIntentAction: ImageIntentAction.ShareImage
) : Module<GalleryViewModel.Base> {

    override fun viewModel() = GalleryViewModel.Base(
        gallery = galleryCommunication,
        repository = repository,
        async = AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
        current = communication,
        selectedModeCommunication = SelectedModeCommunication.Base(),
        navigation = navigation,
        selectedImageCommunication = SelectedImageCommunication.Base(),
        imageIntentAction = imageIntentAction
    )
}
val galleryModule = module {
    includes(imageActionModule)
    val communication = DetailCommunication.Base()
    val galleryCommunication = GalleryCommunication.Base()
   // val imageIntentAction = EmptyIntentAction()

    single<ImageLoader> { get() }
    single<GalleryRepository>{
        GalleryRepository.Base(
            dataSource = CacheDataSourceModule.provideImageRepository(),
            cacheToUiMapper = FavoriteImageCacheToUiMapper.Base(),
            imageLoader = get()
        )
    }
    single<GalleryViewModel> {
        GalleryViewModel.Base(
            gallery = galleryCommunication,
            repository = get(),
            async = AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
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
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
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