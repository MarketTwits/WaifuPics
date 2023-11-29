package com.markettwits.random_image.presentation.components.bottom_pannel.buttons

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.markettwits.random_image.R

import com.markettwits.random_image.presentation.components.report_image.ReportedAlertDialog

@Composable
fun ReportedButton(
    modifier: Modifier,
    enabled: Boolean,
) {
    val isReportedDialog = rememberSaveable { mutableStateOf(false) }
    BasePanelItem(
        modifier = modifier,
        image = R.drawable.ic_flag,
        enabled = enabled,
        onClick = {
            Log.d("mt05", isReportedDialog.toString())
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