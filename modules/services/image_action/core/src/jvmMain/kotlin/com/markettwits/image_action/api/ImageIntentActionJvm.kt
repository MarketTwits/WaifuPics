package com.markettwits.image_action.api

import coil3.Bitmap
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.request.ImageRequest
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skiko.toBufferedImage
import java.awt.Desktop
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class ImageIntentActionJvm(private val platformContext: PlatformContext) :
    ImageIntentAction.Mutable {

    override suspend fun shareImage(imagePath: List<String>) {
        imagePath.forEach {
            shareImage(it)
        }
    }

    override suspend fun shareImage(imagePath: String) {
        loadImageToBitmap(imageUrl = imagePath, value = {
            try {
                val file = bufferedImageToFile(it.toBufferedImage())
                Desktop.getDesktop().browse(file.toURI())
            } catch (e: Exception) {
                println(e)
            }
        })

    }


    override suspend fun launchOpenWith(imagePath: String) {
        loadImageToBitmap(imageUrl = imagePath, value = {
            try {
                val file = bufferedImageToFile(it.toBufferedImage())
                Desktop.getDesktop().open(file)
            } catch (e: Exception) {
                println(e)
            }
        })
    }

    override suspend fun launchUseAs(imagePath: String) {
        loadImageToBitmap(imageUrl = imagePath, value = {
            try {
                val file = bufferedImageToFile(it.toBufferedImage())
                SystemWallpaperManager.acceptChangeSystemWallpaper(file.absolutePath)
            } catch (e: Exception) {
                println(e)
            }
        })

    }

    override suspend fun launchEditAs(imagePath: String) {
        loadImageToBitmap(imageUrl = imagePath, value = {
            try {
                val file = bufferedImageToFile(it.toBufferedImage())
                Desktop.getDesktop().open(file)
            } catch (e: Exception) {
                println(e)
            }
        })
    }

    @OptIn(ExperimentalCoilApi::class)
    private suspend fun loadImageToBitmap(imageUrl: String, value: suspend (Bitmap) -> (Unit)) {
        val imageLoader = coil3.ImageLoader(platformContext)
        val request = ImageRequest.Builder(platformContext)
            .data(imageUrl)
            .build()
        val disposable = imageLoader.enqueue(request)
        disposable.job.await().also { result ->
            val bitmap = result.image?.toBitmap()
                ?: throw IllegalArgumentException("Can't convert bitmap")
            value(bitmap)
        }
    }

    private suspend fun bufferedImageToFile(bufferedImage: BufferedImage): File {
        val path = File(System.getProperty("java.io.tmpdir")).absolutePath
        val tempFile = File("$path\\savedImage.png")
        withContext(Dispatchers.IO) {
            ImageIO.write(bufferedImage, "png", tempFile);
        }
        return tempFile
    }
}

