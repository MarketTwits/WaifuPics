package com.markettwits.random_image.ui.bottom_pannel.panel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.R
import com.markettwits.core_ui.base.BasePanelItem
import com.markettwits.random_image.ui.ImageViewModel
import com.markettwits.random_image.ui.bottom_pannel.BottomPanelUiState
import com.markettwits.random_image.ui.bottom_pannel.report.ReportedAlertDialog

@Composable
fun ReportedButton(
    modifier: Modifier,
    enabled: Boolean,
    viewModel: ImageViewModel
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
               // viewModel.reported()
                isReportedDialog.value = !isReportedDialog.value
            },
            onDismiss = {
                isReportedDialog.value = !isReportedDialog.value
            }

        )
    }
}