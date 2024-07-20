package com.markettwits.core.di.kmp

/**
 * [Factory] is a fun interface which can build data for your classes
 *
 * It's look similar to [Provider] but it's more convenient to use different naming for this two
 */
fun interface Factory<out T> {

    fun create(): T

    fun interface Parametrized<out T, P> {
        fun create(param: P): T
    }
}
