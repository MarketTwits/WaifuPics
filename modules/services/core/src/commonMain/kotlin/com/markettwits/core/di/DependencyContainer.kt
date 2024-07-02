package com.markettwits.core.di

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

interface DependencyContainer {

    fun <T : Any> module(className: KClass<out T >): Module<ViewModel>

    class Error : DependencyContainer {

        override fun <T : Any> module(className: KClass<out T>): Module<ViewModel> {
            throw IllegalArgumentException("unknown className ${className.simpleName}")
        }
    }
}