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
) : ViewModel(), FilterImageType,
    StateCommunication.State<FilterState> {
    init {
        communication.map(FilterState(filter = defaultValue.filter()))
    }

    override fun filter(item: FilterItem) {
        communication.map(
            state().value.copy(filter = filterChecked.checked(item, state().value.filter))
        )
        filterInner()
    }

    override fun state(): StateFlow<FilterState> = communication.state()

    override fun filterInner() {
        val filter = filterChecked.mapFilter(communication.state().value.filter)
        filterCommunication.map(filter)
    }

    override fun toggle() {
        communication.map(state().value.toggle())
    }

    override fun checked(item: FilterItem) {
        filter(item.checked(item.checked))
    }

}

interface FilterImageType {

    fun filter(item: FilterItem)
    fun filterInner()
    fun toggle()
    fun checked(item: FilterItem)

}

interface AgeRatingFilterCommunication : StateCommunication.Mutable<FilterState> {
    class Base : StateCommunication.Abstract<FilterState>(FilterState()),
        AgeRatingFilterCommunication
}

interface FilterCommunication : Communication.Mutable<String> {
    class Base : Communication.Abstract<String>(), FilterCommunication
}