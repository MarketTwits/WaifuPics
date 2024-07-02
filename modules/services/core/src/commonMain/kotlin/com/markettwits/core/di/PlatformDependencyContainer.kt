package com.markettwits.core.di

import kotlin.reflect.KClass

interface PlatformDependencyContainer {

    fun <T : Any> module(className: KClass<T>): PlatformDependency<T>

    class Error : PlatformDependencyContainer {

        override fun <T : Any> module(className: KClass<T>): PlatformDependency<T> {
            throw IllegalArgumentException("unknown className ${className.simpleName}")
        }
    }

}

class EmptyPlatformPlatformDependencyContainer : PlatformDependencyContainer {

    override fun <T : Any> module(className: KClass<T>): PlatformDependency<T> =
        throw NotImplementedError("PlatformDependencyContainer don't provide in initial component")
}
