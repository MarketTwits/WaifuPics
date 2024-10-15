package com.markettwits.core_ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.core.core_ui.generated.resources.Res
import waifupics.modules.core.core_ui.generated.resources.favicon

object DefaultImages {

    @Composable
    fun favicon() : Painter = painterResource(Res.drawable.favicon)

}