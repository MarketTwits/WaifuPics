package com.markettwits.core_ui.provider

import androidx.compose.runtime.Composable
import org.koin.compose.koinInject

@Composable
inline fun <reified T> ApplicationViewModel(): T  = koinInject<T>()
