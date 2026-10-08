package com.markettwits.theme.components

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight

/** Rubik is shared by library components and app content, including emphasized styles. */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun waifuTypography(): Typography {
    val family = FontRubik.family()
    return remember(family) {
        val base = Typography()
        base.copy(
            displayLarge = base.displayLarge.copy(fontFamily = family),
            displayMedium = base.displayMedium.copy(fontFamily = family),
            displaySmall = base.displaySmall.copy(fontFamily = family),
            headlineLarge = base.headlineLarge.copy(fontFamily = family),
            headlineMedium = base.headlineMedium.copy(fontFamily = family),
            headlineSmall = base.headlineSmall.copy(fontFamily = family),
            titleLarge = base.titleLarge.copy(fontFamily = family),
            titleMedium = base.titleMedium.copy(fontFamily = family),
            titleSmall = base.titleSmall.copy(fontFamily = family),
            bodyLarge = base.bodyLarge.copy(fontFamily = family),
            bodyMedium = base.bodyMedium.copy(fontFamily = family),
            bodySmall = base.bodySmall.copy(fontFamily = family),
            labelLarge = base.labelLarge.copy(fontFamily = family),
            labelMedium = base.labelMedium.copy(fontFamily = family),
            labelSmall = base.labelSmall.copy(fontFamily = family),
            displayLargeEmphasized = base.displayLargeEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            displayMediumEmphasized = base.displayMediumEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            displaySmallEmphasized = base.displaySmallEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            headlineLargeEmphasized = base.headlineLargeEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            headlineMediumEmphasized = base.headlineMediumEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            headlineSmallEmphasized = base.headlineSmallEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            titleLargeEmphasized = base.titleLargeEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            titleMediumEmphasized = base.titleMediumEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            titleSmallEmphasized = base.titleSmallEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            bodyLargeEmphasized = base.bodyLargeEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            bodyMediumEmphasized = base.bodyMediumEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            bodySmallEmphasized = base.bodySmallEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            labelLargeEmphasized = base.labelLargeEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            labelMediumEmphasized = base.labelMediumEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
            labelSmallEmphasized = base.labelSmallEmphasized.copy(fontFamily = family, fontWeight = FontWeight.SemiBold),
        )
    }
}
