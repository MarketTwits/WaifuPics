package com.markettwits.core_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.markettwits.core_ui.R

@Immutable
@Stable
object FontRubik{
    val default = FontFamily(Font(R.font.rubik))
    val medium = FontFamily(Font(R.font.rubik_medium))
    val regular = FontFamily(Font(R.font.rubik_regular))
    val bold = FontFamily(Font(R.font.rubik_bold))
    val extraBold = FontFamily(Font(R.font.rubik_extra_bold))
    val light = FontFamily(Font(R.font.rubik_light))
}
