package com.example.fintechtinkoff2023.core.communication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

interface FlowCommunication<T : Any> {
    interface Update<T : Any>{
        suspend fun map(source : T) : Flow<T>
    }
    interface Observe<T : Any>{
        suspend fun fetch(block: suspend (value: T) -> Unit)
    }
    interface Mutable<T : Any> : Update<T>, Observe<T>
    abstract class Abstract<T : Any>(private val flow: Flow<T> = emptyFlow()
    ) : Mutable<T> {
        override suspend fun map(source: T) = flow {
            emit(source)
        }
        override suspend fun fetch(block: suspend (value: T) -> Unit) {
           flow.collect(block)
        }
    }
}