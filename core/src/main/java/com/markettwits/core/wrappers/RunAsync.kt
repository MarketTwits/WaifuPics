package com.markettwits.core.wrappers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync {
    fun <T : Any> runAsync(
        scope: CoroutineScope,
        io: suspend () -> T,
        ui: (T) -> Unit
    )

    suspend fun <T : Any> runAsync(
        io: suspend () -> T,
        ui: (T) -> Unit
    )
    fun clear()
    class Base(
        private val dispatchersList: DispatchersList
    ) : RunAsync {
        private var job: Job? = null
        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            io: suspend () -> T,
            ui: (T) -> Unit
        ) {
            job = scope.launch(dispatchersList.io()) {
                val result = io.invoke()
                withContext(dispatchersList.main()) {
                    ui.invoke(result)
                }
            }

        }

        override suspend fun <T : Any> runAsync(
            io: suspend () -> T,
            ui: (T) -> Unit
        ) {
            withContext(dispatchersList.io()) {
                val result = io.invoke()
                withContext(dispatchersList.main()) {
                    ui.invoke(result)
                }
            }
        }

        override fun clear() {
            job?.cancel()
            job = null
        }
    }
}