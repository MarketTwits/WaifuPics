package com.markettwits.core_ui.di

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject

@Composable
inline fun <reified T> ApplicationViewModel(): T  = koinInject<T>()
