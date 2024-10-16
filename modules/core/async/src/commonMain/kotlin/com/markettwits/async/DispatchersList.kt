package com.markettwits.async

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersList {

    fun io() : CoroutineDispatcher

    fun main() : CoroutineDispatcher

    fun default() : CoroutineDispatcher

    class Base : DispatchersList {

        override fun io(): CoroutineDispatcher = Dispatchers.Main

        override fun main(): CoroutineDispatcher = Dispatchers.Main

        override fun default(): CoroutineDispatcher = Dispatchers.Default
    }
}

expect val dispatchersList : DispatchersList