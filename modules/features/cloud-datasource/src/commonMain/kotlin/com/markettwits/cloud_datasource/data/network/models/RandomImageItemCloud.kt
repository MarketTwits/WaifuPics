package com.markettwits.cloud_datasource.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wraps the given image URL with a proxy service to bypass CORS (Cross-Origin Resource Sharing)
 * issues when using the Ktor client in JS/WASM environments.
 *
 * In Kotlin Multiplatform projects, when targeting JavaScript (JS) or WebAssembly (WASM JS),
 * the `KtorClient` is currently unable to properly handle CORS requests. This can result in
 * failed attempts to load images or other resources from external origins. As a workaround,
 * this method applies a proxy to the image URL using the `wsrv.nl` proxy service, which
 * handles the CORS headers on behalf of the client, allowing the resource to be successfully loaded.
 *
 * For more details on the CORS issue, see the related issue on GitHub:
 * <a href="https://github.com/JetBrains/compose-multiplatform/issues/4544">GitHub issue #4544</a>.
 *
 * @param imageUrl the original URL of the image to be loaded
 * @return a proxied URL that can be safely requested without triggering CORS issues
 */
fun String.proxyImageUrl() : String = "https://wsrv.nl/?url=$this"

@Serializable
data class RandomImageItemCloud(
    @SerialName("color_dominant")
    val color_dominant: List<Int>,
    @SerialName("color_palette")
    val color_palette: List<List<Int>>,
    @SerialName("id")
    val id: Int,
    @SerialName("artist")
    val artist : ArtistCloud?,
    @SerialName("id_v2")
    val id_v2: String,
    @SerialName("sample_url")
    val sample_url: String,
    @SerialName("image_url")
    val image_url : String,
    @SerialName("source")
    val source: String?,
    @SerialName("rating")
    val rating : String
){
    @Serializable
    data class ArtistCloud(
        @SerialName("aliases")
        val aliases: List<String>,
        @SerialName("id")
        val id: Int,
        @SerialName("image_url")
        val image_url: String?,
        @SerialName("links")
        val links: List<String>,
        @SerialName("name")
        val name: String,
    )
}

@Serializable
data class RandomImageCloud(
    @SerialName("items")
    val items : List<RandomImageItemCloud>
)
