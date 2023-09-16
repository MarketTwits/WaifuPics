package com.markettwits.waifupics.view.main.ui.image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.core.communication.Communication
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.waifupics.view.main.data.RandomImageRepository
import com.markettwits.waifupics.view.main.ui.RandomImageUiState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageViewModel(
    private val dispatchers: DispatchersList,
    private val repository: RandomImageRepository,
) : ViewModel(),
    Communication.Observe<RandomImageUiState> {
    val state = MutableLiveData<RandomImageUiState>()

    init {
        state.value = RandomImageUiState.Progress
        fetchRandomImage()
    }
    fun fetchRandomImage() {
        state.value = RandomImageUiState.Progress
        viewModelScope.launch(dispatchers.io()) {
            val result = repository.fetchRandomImage(emptyMap())
            withContext(dispatchers.main()) {
                state.value = result
            }
        }
    }
}


interface ImageCommunication : Communication.Mutable<RandomImageUiState> {
    class Base : Communication.Abstract<RandomImageUiState>(), ImageCommunication
}



