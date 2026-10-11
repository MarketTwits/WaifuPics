package com.markettwits.core_ui.components.image

import coil3.ImageLoader
import coil3.network.ktor3.KtorNetworkFetcherFactory

actual fun coil3.PlatformContext.asyncImageLoader(): coil3.ImageLoader = DefultAsyncImageLoader()