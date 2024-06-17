package com.markettwits.image_action.impl

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import com.markettwits.image_action.api.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.IOException
import java.util.UUID

class ImageLoaderImpl(
    private val context: Context,
) : ImageLoader {

    override suspend fun saveToGallery(imageUrl: String) {
        val name = "NekosAPI, ${UUID.randomUUID()}.png"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.TITLE, name)
            put(MediaStore.Images.Media.DESCRIPTION, name)
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/NekosLand")
        }
        val imageUri =
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        withContext(Dispatchers.IO) {
            try {
                val inputStream = FileInputStream(imageUrl)
                val outputStream = context.contentResolver.openOutputStream(imageUri!!)
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream?.write(buffer, 0, length)
                }
                inputStream.close()
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}
