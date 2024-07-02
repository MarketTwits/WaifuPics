package com.markettwits.core

import android.content.Context
import com.markettwits.core.di.PlatformDependency
import com.markettwits.core.di.PlatformDependencyContainer
import com.markettwits.core.wrappers.DispatchersList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.KClass


class Core(private val context: Context) : PlatformDependencyContainer {

    override fun <T : Any> module(className: KClass<T>): PlatformDependency<T> = when (className) {
        Context::class -> PlatformContext(context)
        DispatchersList::class -> PlatformDispatchers()
        else -> throw IllegalStateException()
    }
}

class PlatformContext<T : Any>(private val context: Context) : PlatformDependency<T> {

    override fun value(): T = context as T
}

class PlatformDispatchers<T : Any> : PlatformDependency<T> {

    override fun value(): T = object : DispatchersList {
        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun main(): CoroutineDispatcher = Dispatchers.Main

        override fun default(): CoroutineDispatcher = Dispatchers.Default
    } as T
}
