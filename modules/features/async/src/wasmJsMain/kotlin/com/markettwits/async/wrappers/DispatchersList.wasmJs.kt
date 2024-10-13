package com.markettwits.async.wrappers

import com.markettwits.async.wrappers.DispatchersList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val dispatchersList: DispatchersList = NativeDispatchersList()

class NativeDispatchersList : DispatchersList {

    override fun io(): CoroutineDispatcher = Dispatchers.Default

    override fun main(): CoroutineDispatcher = Dispatchers.Main

    override fun default(): CoroutineDispatcher = Dispatchers.Default

}