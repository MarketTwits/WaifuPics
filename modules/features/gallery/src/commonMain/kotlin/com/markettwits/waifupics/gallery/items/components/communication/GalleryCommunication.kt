package com.markettwits.waifupics.gallery.items.components.communication

import androidx.compose.runtime.mutableStateListOf
import com.markettwits.async.communication.ListCommunication
import com.markettwits.async.communication.StateCommunication
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