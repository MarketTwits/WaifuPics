package com.markettwits.core.wrappers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface AsyncViewModel<T : Any> {
     fun <T : Any> handleAsync(
        io: suspend () -> T,
        ui: (T) -> Unit
    )
     suspend fun <T : Any> handleAsyncInternal(
        io: suspend () -> T,
        ui: (T) -> Unit
    )
    abstract class Abstract<T : Any>(
        private val runAsync: RunAsync
    ) : AsyncViewModel<T> {
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        protected fun clearAsync() = runAsync.clear()

        override fun <T : Any> handleAsync(
            io: suspend () -> T,
            ui: (T) -> Unit
        ) {
            runAsync.runAsync(scope, io, ui)
        }

        override suspend fun <T : Any> handleAsyncInternal(
            io: suspend () -> T,
            ui: (T) -> Unit
        ) = runAsync.runAsync(io, ui)
    }
    class Base<T : Any>(private val runAsync: RunAsync) : Abstract<T>(runAsync)
}