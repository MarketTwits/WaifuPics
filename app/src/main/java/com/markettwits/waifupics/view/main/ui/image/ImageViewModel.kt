package com.markettwits.waifupics.view.main.ui.image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.core.communication.Communication
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.waifupics.view.filter.FilterItem
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

    val filter = MutableLiveData<List<FilterItem>>(filterCollections())

    init {
        state.value = RandomImageUiState.Progress
        fetchRandomImage()
    }
    fun fetchRandomImage() {
        state.value = RandomImageUiState.Progress
        viewModelScope.launch(dispatchers.io()) {
            val result = repository.fetchRandomImage(inner())
            withContext(dispatchers.main()) {
                state.value = result
            }
        }
    }
    fun filter(selectedItem : FilterItem){
        filter.value = filter.value?.map { if (it.id == selectedItem.id) selectedItem else it }
    }
    private fun inner(): String {
        val filter = filter.value
        val checked = filter?.filter { it.checked } ?: filterCollections()
        val formatString = checked.joinToString(",") { it.value }
        return if (formatString.isEmpty()) "sfw" else formatString
    }

    private fun filterCollections() = listOf(
        FilterItem(1, "Safe for work", "sfw"),
        FilterItem(2, "Questionable", "questionable"),
        FilterItem(3, "Suggestive", "suggestive"),
        FilterItem(4,"Borderline", "borderline"),
        FilterItem(5,"Explicit (18+)", "explicit")
    )

}


interface ImageCommunication : Communication.Mutable<RandomImageUiState> {
    class Base : Communication.Abstract<RandomImageUiState>(), ImageCommunication
}



