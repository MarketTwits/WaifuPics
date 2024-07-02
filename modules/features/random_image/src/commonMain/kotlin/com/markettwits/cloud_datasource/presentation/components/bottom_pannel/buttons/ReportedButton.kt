package com.markettwits.cloud_datasource.presentation.components.bottom_pannel.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.markettwits.cloud_datasource.presentation.components.report_image.ReportedAlertDialog
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.ic_flag

@Composable
fun ReportedButton(
    modifier: Modifier,
    enabled: Boolean,
) {
    val isReportedDialog = rememberSaveable { mutableStateOf(false) }
    BasePanelItem(
        modifier = modifier,
        image = painterResource(Res.drawable.ic_flag) ,
        enabled = enabled,
        onClick = {
            isReportedDialog.value = !isReportedDialog.value
        }
    )
    if (isReportedDialog.value) {
        ReportedAlertDialog(
            onSubmit = {
                isReportedDialog.value = !isReportedDialog.value
            },
            onDismiss = {
                isReportedDialog.value = !isReportedDialog.value
            }

        )
    }
}