package com.markettwits.data.media_info

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.PhotoAlbum
import com.markettwits.presentation.screens.detail.info.MediaInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class AbstractImageInfoToUiMapper : ImageInfoToUiMapper {
    fun metaDateToUi(fileSize: String, fileWidth: Int, fileHeight: Int): String {
        return "$fileSize * $fileWidth x $fileHeight"
    }

    fun mediaInfo(label: String, metadata: String, path: String): List<MediaInfo> = listOf(
        MediaInfo(1, "Label", label, Icons.Default.AddPhotoAlternate),
        MediaInfo(2, "Metadata", metadata, Icons.Default.MarkEmailRead),
        MediaInfo(3, "Path", path, Icons.Default.PhotoAlbum),
    )

    fun mapTimeToUi(time: Long): String {
        val dateFormat = SimpleDateFormat("MMMM d, yyyy * hh:mm a", Locale.getDefault())
        return dateFormat.format(Date(time))
    }

    fun formatFileSize(bytes: Long): String {
        val kilobytes = bytes / 1024.0
        val megabytes = kilobytes / 1024.0

        return when {
            megabytes >= 1.0 -> String.format("%.2f MB", megabytes)
            kilobytes >= 1.0 -> String.format("%.2f KB", kilobytes)
            else -> String.format("%d bytes", bytes)
        }
    }
}