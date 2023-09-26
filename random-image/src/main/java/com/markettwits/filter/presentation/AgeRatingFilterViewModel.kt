package com.markettwits.filter.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.Communication
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.SaveAndRestoreState
import com.markettwits.core.wrappers.WrapBundle
import com.markettwits.filter.data.StaticCacheDataSource
import com.markettwits.`random-image`.domain.FilterChecked
import com.markettwits.waifupics.filter.presentation.FilterState
import kotlinx.coroutines.flow.StateFlow

class AgeRatingFilterViewModel(
    private val filterChecked: FilterChecked,
    private val communication: AgeRatingFilterCommunication,
    private val filterCommunication: FilterCommunication,
    private val defaultValue: StaticCacheDataSource,
    private val saveAndRestore : SaveAndRestoreState
) : ViewModel(), FilterImageType,
    StateCommunication.State<FilterState> {

    private val callback = object : SaveAndRestoreState.Callback{
        override fun save(bundle: WrapBundle) {
            bundle.save("FilterState", communication.state().value)
            Log.d("mt05", "Save: $bundle")
        }
        override fun restored(bundle: WrapBundle) {
            Log.d("mt05", "Restore: $bundle")
            communication.map(bundle.read("FilterState", FilterState::class.java))
        }
    }
    fun init(firstRun : Boolean){
        if (firstRun){
            if (communication.state().value.filter.isEmpty()){
                communication.map(FilterState(filter = defaultValue.filter()))
            }
            Log.d("mt05", "First run : AgeRatingFilter")
        }
    }
    init {
        //saveAndRestore.subscribe(callback)
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