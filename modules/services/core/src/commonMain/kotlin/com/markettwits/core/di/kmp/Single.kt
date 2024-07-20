package com.markettwits.core.di.kmp

/**
 * [Single] is a singleton value which will be a unique and single instant
 *
 * This can be helpful for repository storing, DB connection and etc
 *
 * Also implements [Lazy]
 */
class Single<out T>(
    factory: Factory<T>
) : Dependency<T>, Lazy<T> by lazy(initializer = { factory.create() })
