package com.markettwits.image_action.impl

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.fetch.Response
import org.w3c.files.Blob


class ImageLoaderJS :
    com.markettwits.image_action.api.ImageLoader {

    override suspend fun saveToGallery(imageUrl: String) {
        try {
            val response: Response = window.fetch(imageUrl).await()

            if (!response.ok) {
                throw Exception("Failed to fetch image. Status: ${response.status}")
            }

            val blob: Blob = response.blob().await()

            val blobUrl = URL.createObjectURL(blob)

            val a = document.createElement("a") as HTMLAnchorElement
            a.href = blobUrl
            a.download = "WaifuPicsImage"  // The filename for the downloaded image

            document.body?.appendChild(a)

            a.click()

            document.body?.removeChild(a)

            URL.revokeObjectURL(blobUrl)

        } catch (e: Throwable) {
            console.error("Error downloading image: ${e.message}")
        }
    }

}