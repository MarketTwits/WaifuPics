package com.markettwits.waifupics.random.components.bottom_info

import androidx.compose.ui.platform.ClipEntry
import java.awt.datatransfer.StringSelection

internal actual fun plainTextClipEntry(text: String): ClipEntry = ClipEntry(StringSelection(text))
