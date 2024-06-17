package com.markettwits.core.di

import androidx.lifecycle.ViewModel

interface DependencyContainer {

    fun <T> module(className: Class<out T>): Module<ViewModel>

    class Error : DependencyContainer {

//        override fun module(className: Class<out ViewModel>): Module<ViewModel> =
//            throw IllegalArgumentException("unknown className ${className.name}")

        override fun <T> module(className: Class<out T>): Module<ViewModel> =
            throw IllegalArgumentException("unknown className ${className.name}")
    }
}