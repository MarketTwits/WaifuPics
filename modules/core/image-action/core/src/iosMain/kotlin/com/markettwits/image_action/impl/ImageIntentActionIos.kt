package com.markettwits.image_action.impl

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.request.ImageRequest
import coil3.toBitmap
import com.markettwits.image_action.api.ImageIntentAction
import kotlinx.cinterop.refTo
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image.Companion.makeFromBitmap
import org.jetbrains.skia.ImageInfo
import platform.CoreFoundation.CFDataCreate
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorRenderingIntent
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGDataProviderCreateWithCFData
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGImageCreate
import platform.CoreGraphics.kCGBitmapByteOrder32Little
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIViewController
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

class ImageIntentActionIos(private val context: PlatformContext) : ImageIntentAction.Mutable {

    override suspend fun shareImage(imagePath: List<String>) {
        val images = mutableListOf<UIImage>()
        imagePath.forEach { url ->
            loadImage(imageUrl = url, result = { image ->
                images.add(image)
            })
        }
        getTopViewController(callback = { uiViewController ->
            val activityViewController = UIActivityViewController(images, null)
            uiViewController?.presentViewController(activityViewController, true, null)
        })
    }

    override suspend fun shareImage(imagePath: String) {
        loadImage(imageUrl = imagePath, result = { image ->
            getTopViewController(callback = { uiViewController ->
                val activityViewController = UIActivityViewController(listOf(image), null)
                uiViewController?.presentViewController(activityViewController, true, null)
            })
        })
    }

    @OptIn(ExperimentalCoilApi::class)
    suspend fun loadImage(imageUrl: String, result: (UIImage) -> Unit) {
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()
        val disposable = imageLoader.enqueue(request)
        disposable.job.await().also { value ->
            val image = value.image?.let { convertImage(it) }
            if (image != null) {
                result(image)
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    fun convertImage(image: coil3.Image): UIImage? {
        val bitmap: Bitmap = image.toBitmap()
        val skikoImage = makeFromBitmap(bitmap)
        val skikoImagePixelMap = skikoImage.peekPixels()
        if (skikoImagePixelMap != null) {
            val cfDataRef = CFDataCreate(
                allocator = null,
                bytes = skikoImagePixelMap.buffer.bytes.asUByteArray().refTo(0),
                length = skikoImagePixelMap.buffer.size.toLong()
            )
            val cgImageRef = CGImageCreate(
                width = skikoImage.width.toULong(),
                height = skikoImage.height.toULong(),
                bitsPerComponent = 8u,
                bitsPerPixel = 32u,
                bytesPerRow = (skikoImage.width * 4).toULong(),
                space = CGColorSpaceCreateDeviceRGB(),
                bitmapInfo = kCGBitmapByteOrder32Little or CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst.value,
                provider = CGDataProviderCreateWithCFData(cfDataRef),
                decode = null,
                shouldInterpolate = true,
                intent = CGColorRenderingIntent.kCGRenderingIntentDefault
            )
            return UIImage(cGImage = cgImageRef)
        }
        return null
    }

    override suspend fun launchOpenWith(imagePath: String) {
        shareImage(imagePath)
    }

    override suspend fun launchUseAs(imagePath: String) {
        shareImage(imagePath)
    }

    private fun getTopViewController(callback: (UIViewController?) -> Unit) {
        dispatch_async(dispatch_get_main_queue()) {
            var topController = UIApplication.sharedApplication.keyWindow?.rootViewController
            while (topController?.presentedViewController != null) {
                topController = topController.presentedViewController
            }
            callback(topController)
        }
    }
}