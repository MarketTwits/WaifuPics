package com.markettwits.async.wrappers

import com.markettwits.async.wrappers.DispatchersList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val dispatchersList: DispatchersList = DispatchersListIos()


class DispatchersListIos : DispatchersList {

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun main(): CoroutineDispatcher = Dispatchers.Main

    override fun default(): CoroutineDispatcher = Dispatchers.Default

}
