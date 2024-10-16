package com.markettwits.theme.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import waifupics.modules.core.theme.generated.resources.Res
import waifupics.modules.core.theme.generated.resources.rubik
import waifupics.modules.core.theme.generated.resources.rubik_bold
import waifupics.modules.core.theme.generated.resources.rubik_extra_bold
import waifupics.modules.core.theme.generated.resources.rubik_light
import waifupics.modules.core.theme.generated.resources.rubik_medium
import waifupics.modules.core.theme.generated.resources.rubik_regular

@Immutable
object FontRubik {
    @Composable
    fun default() = FontFamily(Font(Res.font.rubik))

    @Composable
    fun medium() = FontFamily(Font(Res.font.rubik_medium))

    @Composable
    fun regular() = FontFamily(Font(Res.font.rubik_regular))

    @Composable
    fun bold() = FontFamily(Font(Res.font.rubik_bold))

    @Composable
    fun extraBold() = FontFamily(Font(Res.font.rubik_extra_bold))

    @Composable
    fun light() = FontFamily(Font(Res.font.rubik_light))
}
