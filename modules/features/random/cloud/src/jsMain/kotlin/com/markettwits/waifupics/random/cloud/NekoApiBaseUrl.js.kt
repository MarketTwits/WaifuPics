package com.markettwits.waifupics.random.cloud

@Suppress("DEPRECATION")
internal actual val nekoApiBaseUrl: String = js("globalThis.WAIFUPICS_NEKOS_API_URL") as String
