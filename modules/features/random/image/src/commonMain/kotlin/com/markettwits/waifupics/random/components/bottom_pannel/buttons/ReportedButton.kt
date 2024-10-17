package com.markettwits.waifupics.random.components.bottom_pannel.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.random.report.api.ReportedAlertDialog
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.ic_flag

@Composable
fun ReportedButton(
    modifier: Modifier,
    imageId : Int,
    enabled: Boolean,
) {
    val isReportedDialog = rememberSaveable { mutableStateOf(false) }
    BasePanelItem(
        modifier = modifier,
        image = painterResource(Res.drawable.ic_flag),
        enabled = enabled,
        onClick = {
            isReportedDialog.value = !isReportedDialog.value
        }
    )
    if (isReportedDialog.value) {
        ReportedAlertDialog(
            viewModel = ApplicationViewModel(),
            imageId = imageId,
            onDismiss = {
                isReportedDialog.value = !isReportedDialog.value
            }
        )
    }
}