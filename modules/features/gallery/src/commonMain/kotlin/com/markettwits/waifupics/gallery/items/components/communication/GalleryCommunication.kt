package com.markettwits.waifupics.gallery.items.components.communication

import androidx.compose.runtime.mutableStateListOf
import com.markettwits.waifupics.communication.ListCommunication
import com.markettwits.waifupics.communication.StateCommunication
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUiState

interface GalleryCommunication : StateCommunication.Mutable<ImageFavoriteUiState> {
    class Base : StateCommunication.Abstract<ImageFavoriteUiState>(ImageFavoriteUiState.Empty),
        GalleryCommunication
}

interface DetailCommunication : StateCommunication.Mutable<ImageFavoriteUi> {
    class Base : StateCommunication.Abstract<ImageFavoriteUi>(ImageFavoriteUi.Initial),
        DetailCommunication
}

interface SelectedModeCommunication : StateCommunication.Mutable<Boolean> {
    class Base : StateCommunication.Abstract<Boolean>(false),
        SelectedModeCommunication
}

interface SelectedImageCommunication : ListCommunication.Mutable<ImageFavoriteUi> {
    class Base : ListCommunication.Abstract<ImageFavoriteUi>(mutableStateListOf()),
        SelectedImageCommunication
}

interface GalleryScreenLabelsCommunication :
    StateCommunication.Mutable<GalleryScreenViewModel.Labels> {
    class Base :
        StateCommunication.Abstract<GalleryScreenViewModel.Labels>(GalleryScreenViewModel.Labels.Empty),
        GalleryScreenLabelsCommunication
}
