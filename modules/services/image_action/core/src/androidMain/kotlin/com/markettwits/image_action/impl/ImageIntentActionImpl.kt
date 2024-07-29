package com.markettwits.image_action.impl

import android.content.Context
import android.content.Intent
import androidx.core.app.ShareCompat
import com.markettwits.image_action.api.ImageIntentAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageIntentActionImpl(
    private val context: Context,
    private val imageFileWrapper: ImageFileWrapper
) : ImageIntentAction.Mutable {

    override suspend fun shareImage(imagePath: List<String>) {
        withContext(Dispatchers.Default) {
            val intent = ShareCompat
                .IntentBuilder(context)
                .setType(TYPE_IMAGE)
            imagePath.forEach {
                imageFileWrapper.imageUrlToUri(it) {
                    intent.addStream(it)
                }
            }
            context.startActivity(
                intent
                    .createChooserIntent()
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override suspend fun shareImage(imagePath: String) {
        imageFileWrapper.imageUrlToUri(imagePath) { uri ->
            val intent = ShareCompat
                .IntentBuilder(context)
                .setType(TYPE_IMAGE)
                .addStream(uri)
                .intent
            context.startActivity(
                Intent
                    .createChooser(intent, "Share Image")
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            imageFileWrapper.deleteFileByUri(uri)

        }
    }

    override suspend fun launchOpenWith(imagePath: String) {
        withContext(Dispatchers.Default) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                imageFileWrapper.imageUrlToUri(imagePath) { uri ->
                    setDataAndType(uri, TYPE_IMAGE)
                }
                putExtra(MIME_TYPE, TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent
                    .createChooser(intent, INTENT_ACTION_OPEN_WITH)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override suspend fun launchUseAs(imagePath: String) {
        withContext(Dispatchers.Default) {
            val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                imageFileWrapper.imageUrlToUri(imagePath) { uri ->
                    setDataAndType(uri, TYPE_IMAGE)
                }
                putExtra(MIME_TYPE, TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent
                    .createChooser(intent, INTENT_ACTION_SET_AS)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override suspend fun launchEditAs(imagePath: String) {
        withContext(Dispatchers.Default) {
            val intent = Intent(Intent.ACTION_EDIT).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                imageFileWrapper.imageUrlToUri(imagePath) { uri ->
                    setDataAndType(uri, TYPE_IMAGE)
                }
                putExtra(MIME_TYPE, TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent.createChooser(intent, INTENT_ACTION_EDIT_IMAGE)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    companion object {
        const val INTENT_ACTION_EDIT_IMAGE = "Edit"
        const val INTENT_ACTION_SET_AS = "Set as"
        const val INTENT_ACTION_OPEN_WITH = "Set as"
        const val TYPE_IMAGE = "image/*"
        const val MIME_TYPE = "mimeType"
    }
}
