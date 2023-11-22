package com.markettwits.data.media_info

import com.markettwits.presentation.detail.info.MediaInfoUiState

interface ImageInfoToUiMapper {
    fun map(image: ExifAttributes): MediaInfoUiState
    class Base : AbstractImageInfoToUiMapper() {
        override fun map(image: ExifAttributes): MediaInfoUiState {
            return MediaInfoUiState(
                mediaInfo(
                    image.label,
                    metaDateToUi(
                        formatFileSize(image.fileSize),
                        fileWidth = image.imageWidth,
                        fileHeight = image.imageHeight
                    ),
                    path = image.filePath
                ),
                mapTimeToUi(image.createdDate)
            )
        }
    }
}