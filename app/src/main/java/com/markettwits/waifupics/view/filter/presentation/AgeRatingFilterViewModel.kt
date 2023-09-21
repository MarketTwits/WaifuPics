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
    private val dialogStateCommunication: DialogStateCommunication,
    private val defaultValue: StaticCacheDataSource
) : ViewModel(), AgeRatingFilterResult, FilterImageType, OpenCloseFilter,
    StateCommunication.UiMutable<List<FilterItem>> {
    init {
        communication.map(defaultValue.filter())
    }

    override fun filter(selectedItem: FilterItem) {
        communication.map(filterChecked.checked(selectedItem, fetch().value))
        filterInner()
    }

    override fun updateState(function: (List<FilterItem>) -> List<FilterItem>) {
        communication.updateState(function)
    }
    override fun fetch(): StateFlow<List<FilterItem>> = communication.fetch()

    override fun filterInner() {
        val filter = filterChecked.mapFilter(communication.fetch().value)
        filterCommunication.map(filter)
    }
    override fun openMenu() {
        dialogStateCommunication.map(dialogStateCommunication != dialogStateCommunication.fetch())
    }

    override fun dialogState() = dialogStateCommunication.fetch()

    override fun updateOpenDialogState(function: (Boolean) -> Boolean) {
        dialogStateCommunication.updateState(function)
    }
}

interface OpenCloseFilter {
    fun openMenu()
    fun dialogState() : StateFlow<Boolean>
    fun updateOpenDialogState(function: (Boolean) -> Boolean)
}

interface FilterImageType {
    fun filter(selectedItem: FilterItem)
}

interface AgeRatingFilterResult {
    fun filterInner()
}

interface AgeRatingFilterCommunication : StateCommunication.Mutable<List<FilterItem>> {
    class Base : StateCommunication.Abstract<List<FilterItem>>(emptyList()),
        AgeRatingFilterCommunication
}

interface DialogStateCommunication : StateCommunication.Mutable<Boolean> {
    class Base : StateCommunication.Abstract<Boolean>(false),
        DialogStateCommunication
}


interface FilterCommunication : Communication.Mutable<String> {
    class Base : Communication.Abstract<String>(), FilterCommunication
}