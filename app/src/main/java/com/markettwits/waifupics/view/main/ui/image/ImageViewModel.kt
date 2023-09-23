package com.markettwits.waifupics.view.main.ui.image

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.waifupics.view.filter.presentation.FilterCommunication
import com.markettwits.waifupics.view.main.data.RandomImageRepository
import com.markettwits.waifupics.view.main.ui.RandomImageUiState

class ImageViewModel(
    private val filterResult: FilterCommunication,
    private val async: AsyncViewModel.Abstract<RandomImageUiState>,
    private val randomImageCommunication: RandomImageCommunication,
    private val repository: RandomImageRepository,
) : ViewModel(), StateCommunication.State<RandomImageUiState>, RandomImage {
    init {
        randomImageCommunication.map(RandomImageUiState.Progress)
        fetchRandomImage()
    }

    override fun fetchRandomImage() {
        randomImageCommunication.map(RandomImageUiState.Progress)
        async.handleAsync({
            repository.fetchRandomImage(filterResult.fetch() ?: "sfw")
        }) {
            randomImageCommunication.map(it)
        }
    }

    override fun state() = randomImageCommunication.state()
}

interface RandomImage {
    fun fetchRandomImage()
}

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageUiState> {
    class Base : StateCommunication.Abstract<RandomImageUiState>(RandomImageUiState.Initial),
        RandomImageCommunication

}


