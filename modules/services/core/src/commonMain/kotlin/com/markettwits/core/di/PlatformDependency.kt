package com.markettwits.core.di

interface PlatformDependency<T : Any> {
    fun value() : T
}

