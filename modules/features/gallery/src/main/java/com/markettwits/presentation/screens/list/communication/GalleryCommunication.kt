package com.markettwits.presentation.screens.list.communication

import androidx.compose.runtime.mutableStateListOf
import com.markettwits.core.communication.ListCommunication
import com.markettwits.core.communication.StateCommunication
import com.markettwits.presentation.screens.ImageFavoriteUi

interface GalleryCommunication : StateCommunication.Mutable<List<ImageFavoriteUi>> {
    class Base : StateCommunication.Abstract<List<ImageFavoriteUi>>(listOf()), GalleryCommunication
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