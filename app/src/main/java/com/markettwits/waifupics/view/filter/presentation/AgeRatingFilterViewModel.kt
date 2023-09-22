package com.markettwits.waifupics.view.filter.presentation

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.Communication
import com.markettwits.core.communication.StateCommunication
import com.markettwits.waifupics.view.filter.data.StaticCacheDataSource
import com.markettwits.waifupics.view.main.domain.FilterChecked
import kotlinx.coroutines.flow.StateFlow

class AgeRatingFilterViewModel(
    private val filterChecked: FilterChecked,
    private val communication: AgeRatingFilterCommunication,
    private val filterCommunication: FilterCommunication,
    private val defaultValue: StaticCacheDataSource,
) : ViewModel(), AgeRatingFilterResult, FilterImageType,
    StateCommunication.UiMutable<FilterState> {
    init {
        communication.map(FilterState(filter = defaultValue.filter()))
    }

    override fun filter(selectedItem: FilterItem) {
        communication.map(
            communication.state().value.copy(
                filter = filterChecked.checked(
                    selectedItem,
                    state().value.filter
                )
            )
        )
        filterInner()
    }

    override fun updateState(function: (FilterState) -> FilterState) {
        communication.updateState(function)
    }

    override fun state(): StateFlow<FilterState> = communication.state()

    override fun filterInner() {
        val filter = filterChecked.mapFilter(communication.state().value.filter)
        filterCommunication.map(filter)
    }
    fun toggle(){
        communication.map(state().value.toggle())
    }

}

interface FilterImageType {
    fun filter(selectedItem: FilterItem)
}

interface AgeRatingFilterResult {
    fun filterInner()
}

interface AgeRatingFilterCommunication : StateCommunication.Mutable<FilterState> {
    class Base : StateCommunication.Abstract<FilterState>(FilterState()),
        AgeRatingFilterCommunication
}

interface FilterCommunication : Communication.Mutable<String> {
    class Base : Communication.Abstract<String>(), FilterCommunication
}