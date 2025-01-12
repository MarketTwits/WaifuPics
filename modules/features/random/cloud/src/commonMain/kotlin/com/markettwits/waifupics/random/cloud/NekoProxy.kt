package com.markettwits.waifupics.random.cloud

/**
 * Wraps the given image URL with a proxy service to bypass CORS
 * (Cross-Origin Resource Sharing) issues when using the Ktor client in
 * JS/WASM environments.
 *
 * In Kotlin Multiplatform projects, when targeting JavaScript (JS) or
 * WebAssembly (WASM JS), the `KtorClient` is currently unable to properly
 * handle CORS requests. This can result in failed attempts to load images
 * or other resources from external origins. As a workaround, this method
 * applies a proxy to the image URL using the `wsrv.nl` proxy service,
 * which handles the CORS headers on behalf of the client, allowing the
 * resource to be successfully loaded.
 *
 * For more details on the CORS issue, see the related issue on GitHub: <a
 * href="https://github.com/JetBrains/compose-multiplatform/issues/4544">GitHub
 * issue #4544</a>.
 *
 * @param imageUrl the original URL of the image to be loaded
 * @return a proxied URL that can be safely requested without triggering
 *    CORS issues
 */
fun String.proxyImageUrl(): String = "https://wsrv.nl/?url=$this"