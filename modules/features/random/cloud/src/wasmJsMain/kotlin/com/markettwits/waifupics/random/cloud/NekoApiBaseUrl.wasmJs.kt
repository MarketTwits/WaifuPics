package com.markettwits.waifupics.random.cloud

import kotlin.JsFun
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("() => globalThis.WAIFUPICS_NEKOS_API_URL")
private external fun readNekosApiUrl(): String

internal actual val nekoApiBaseUrl: String = readNekosApiUrl()
