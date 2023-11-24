package com.markettwits.presentation.image_action

import android.content.Context
import android.content.Intent
import androidx.core.app.ShareCompat
import com.markettwits.core_ui.image.ImageFileWrapper
import com.markettwits.core_ui.image.ImageIntentAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageIntentActionImpl(
    private val context: Context,
    private val imageFileWrapper: ImageFileWrapper
) : ImageIntentAction.Mutable {
    override suspend fun shareImage(imagePath: String) {
        val intent = ShareCompat
            .IntentBuilder(context)
            .setType(TYPE_IMAGE)
            .addStream(imageFileWrapper.pathToUri(imagePath))
            .intent
        context.startActivity(
            Intent.createChooser(intent, "Share Image").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )

    }

    override suspend fun launchOpenWith(imagePath: String) {
        withContext(Dispatchers.Default) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(imageFileWrapper.pathToUri(imagePath), TYPE_IMAGE)
                putExtra("mimeType", TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent.createChooser(intent, "Open With").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override suspend fun launchUseAs(imagePath: String) {
        withContext(Dispatchers.Default) {
            val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(imageFileWrapper.pathToUri(imagePath), TYPE_IMAGE)
                putExtra("mimeType", TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent.createChooser(intent, "setAs")
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override suspend fun launchEditAs(imagePath: String) {
        withContext(Dispatchers.Default) {
            val intent = Intent(Intent.ACTION_EDIT).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(imageFileWrapper. pathToUri(imagePath), TYPE_IMAGE)
                putExtra("mimeType", TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent.createChooser(intent, "Edit").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    companion object {
        const val TYPE_IMAGE = "image/*"
    }
}
