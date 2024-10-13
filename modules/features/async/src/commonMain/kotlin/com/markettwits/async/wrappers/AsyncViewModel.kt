package com.markettwits.async.wrappers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface AsyncViewModel<T : Any> {

     fun handleAsyncSingle(io : suspend () -> Unit)

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
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
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

        override fun handleAsyncSingle(io: suspend () -> Unit) = runAsync.runAsync(scope, io)

    }

    class Base<T : Any>(runAsync: RunAsync) : Abstract<T>(runAsync)
}