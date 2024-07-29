package com.markettwits.presentation.screens.detail

import androidx.lifecycle.ViewModel
import com.markettwits.cache_datasource.GalleryRepository
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.presentation.copy.SystemService
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.ImageFavoriteUi
import com.markettwits.presentation.screens.ImageFavoriteUiState
import com.markettwits.presentation.screens.list.communication.DetailCommunication
import com.markettwits.presentation.screens.list.communication.GalleryCommunication
import kotlinx.coroutines.flow.StateFlow

interface GalleryScreenViewModel {

    fun onClickDelete()

    fun onClickEditImage()

    fun onClickSetImageAs()

    fun onClickSaveToGallery()

    fun onClickShareImage()

    fun onClickPop()

    fun onClickCopy(text: String)

    fun currentImage(): StateFlow<ImageFavoriteUi>

    fun setCurrentItem(index: Int)

    fun state(): StateFlow<ImageFavoriteUiState>

    class Base(
        private val item: DetailCommunication,
        private val list: GalleryCommunication,
        private val async: AsyncViewModel<Unit>,
        private val repository: GalleryRepository,
        private val imageIntentAction: ImageIntentAction.Mutable,
        private val navigation: GalleryRouter,
        private val systemService: SystemService,
    ) : ViewModel(), GalleryScreenViewModel {

        override fun state() = list.state()

        override fun currentImage(): StateFlow<ImageFavoriteUi> = item.state()

        override fun onClickPop() {
            navigation.pop()
        }

        override fun onClickCopy(text: String) {
            systemService.copy(text)
        }

        override fun onClickDelete() {
            async.handleAsyncSingle {
                repository.delete(item.state().value.id)
            }
        }

        override fun onClickEditImage() {
            async.handleAsyncSingle {
                imageIntentAction.launchOpenWith(item.state().value.imageUrl)
            }
        }

        override fun onClickSetImageAs() {
            async.handleAsyncSingle {
                imageIntentAction.launchUseAs(item.state().value.imageUrl)
            }
        }

        override fun onClickSaveToGallery() {
           async.handleAsyncSingle {
               repository.saveToGallery(item.state().value.imageUrl)
           }
        }

        override fun onClickShareImage() {
            async.handleAsyncSingle {
                imageIntentAction.shareImage(item.state().value.imageUrl)
            }
        }

        override fun setCurrentItem(index: Int) {
            if (index < 0 || list.state().value.items.isEmpty()) {
                onClickPop()
            } else {
                item.map(list.state().value.items[index])
            }
        }

    }
}

interface CurrentItemCommunication : StateCommunication.Mutable<ImageFavoriteUi> {
    class Base : StateCommunication.Abstract<ImageFavoriteUi>(ImageFavoriteUi.Initial),
        CurrentItemCommunication
}
