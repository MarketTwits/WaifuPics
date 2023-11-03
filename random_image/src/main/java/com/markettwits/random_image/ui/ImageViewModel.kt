package com.markettwits.random_image.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.random_image.data.RandomImageRepository

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
            repository.fetchRandomImage(filterResult.fetch() ?: listOf("safe"))
        }) {
            randomImageCommunication.map(it)
        }
    }

    override fun addToFavorite(url : String, protected : Boolean, context: Context) {
        async.handleAsync({
            repository.addToFavorite(url, protected)
        }){}
    }

    override fun state() = randomImageCommunication.state()
}

interface RandomImage {
    fun fetchRandomImage()
    fun addToFavorite(url : String, protected : Boolean,context: Context)
}

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageUiState> {
    class Base : StateCommunication.Abstract<RandomImageUiState>(RandomImageUiState.Initial),
        RandomImageCommunication
}


