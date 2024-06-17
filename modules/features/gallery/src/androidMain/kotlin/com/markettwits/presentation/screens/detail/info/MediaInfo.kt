package com.markettwits.presentation.screens.detail.info

import androidx.compose.ui.graphics.vector.ImageVector

data class MediaInfo(val id: Int, val label: String, val content: String, val icon: ImageVector)

data class MediaInfoUiState(val mediaInfo: List<MediaInfo>, val created: String)