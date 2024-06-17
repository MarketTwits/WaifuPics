package com.markettwits.core
import android.content.Context
import com.markettwits.core.wrappers.DispatchersList


class Core(
    private val context: Context,
) :  ProvideDispatchersList {

    private val dispatchersList = DispatchersList.Base()

    fun context() = context

    override fun dispatchers(): DispatchersList = dispatchersList

}
interface ProvideDispatchersList{
    fun dispatchers() : DispatchersList
}
