package com.markettwits.data.media_info

import android.graphics.BitmapFactory
import java.io.File


interface ExifServiceWrapper {
    fun imageInfo(path: String) : ExifAttributes
    class Base() : ExifServiceWrapper {
        override fun imageInfo(path: String): ExifAttributes {
            val file = File(path)

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)

            return ExifAttributes(
                label = file.name,
                fileSize = file.length(),
                imageWidth = options.outWidth,
                imageHeight = options.outHeight,
                filePath = file.absolutePath,
                mimeType = options.outMimeType,
                createdDate = file.lastModified()
            )
        }
    }
}




