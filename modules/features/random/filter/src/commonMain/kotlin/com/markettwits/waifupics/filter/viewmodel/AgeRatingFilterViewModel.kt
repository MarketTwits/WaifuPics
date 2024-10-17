package com.markettwits.waifupics.filter.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.waifupics.communication.StateCommunication
import com.markettwits.waifupics.filter.data.FilterDataSource
import com.markettwits.waifupics.filter.domain.FilterChecked
import com.markettwits.waifupics.filter.model.FilterItem
import com.markettwits.waifupics.filter.model.FilterState
import kotlinx.coroutines.flow.StateFlow

class AgeRatingFilterViewModel(
    private val filterChecked: FilterChecked,
    private val communication: AgeRatingFilterCommunication,
    private val filterCommunication: FilterCommunication,
    private val filterDataSource: FilterDataSource,
) : ViewModel(), FilterImageType,
    StateCommunication.State<FilterState> {

    init {
        communication.map(FilterState(filter = filterDataSource.filter()))
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

interface FilterCommunication : StateCommunication.Mutable<List<String>> {
    class Base : StateCommunication.Abstract<List<String>>(listOf("safe")), FilterCommunication
}