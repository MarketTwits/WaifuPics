package com.markettwits.waifupics.communication

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface StateCommunication {

    interface Update<T : Any> {
        fun map(source: T)
    }

    interface UpdateState<T : Any> {
        fun updateState(function: (T) -> T)
    }

    interface State<T : Any> {
        fun state(): kotlinx.coroutines.flow.StateFlow<T>
    }

    interface Mutable<T : Any> : Update<T>, State<T>, UpdateState<T>

    interface UiMutable<T : Any> : UpdateState<T>, State<T>

    abstract class Abstract<T : Any>(
        initialValue: T
    ) : Mutable<T> {
        private val state = kotlinx.coroutines.flow.MutableStateFlow(initialValue)

        override fun map(source: T) {
            state.value = source
        }
        override fun state() = state.asStateFlow()

        override fun updateState(function: (T) -> T) = state.update(function)
    }
}