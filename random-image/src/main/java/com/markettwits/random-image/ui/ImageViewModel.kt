package com.markettwits.waifupics.view.main.ui.image

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.database.FavoriteImageRepository
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.`random-image`.ui.RandomImageUiState
import com.markettwits.waifupics.view.main.data.RandomImageRepository

class ImageViewModel(
    private val filterResult: FilterCommunication,
    private val async: AsyncViewModel.Abstract<RandomImageUiState>,
    private val randomImageCommunication: RandomImageCommunication,
    private val repository: RandomImageRepository,
    private val cache : FavoriteImageRepository,
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

    override fun addToFavorite(url : String, ageRating : String) {
        async.handleAsync({
            cache.add(url, ageRating)
        }){}
    }

    override fun state() = randomImageCommunication.state()
}

interface RandomImage {
    fun fetchRandomImage()
    fun addToFavorite(url : String, ageRating : String)
}

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageUiState> {
    class Base : StateCommunication.Abstract<RandomImageUiState>(RandomImageUiState.Initial),
        RandomImageCommunication
}


