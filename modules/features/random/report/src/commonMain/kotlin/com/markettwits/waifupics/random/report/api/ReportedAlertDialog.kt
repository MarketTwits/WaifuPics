package com.markettwits.waifupics.random.report.api

import androidx.compose.runtime.*
import com.markettwits.core_ui.components.design.WaifuDialog
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.report.generated.resources.*

@Composable
fun ReportedAlertDialog(imageId: Int, viewModel: ReportImageViewModel, onDismiss: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val label by viewModel.labels.collectAsState()
    LaunchedEffect(label) {
        if (label == ReportImageViewModel.Labels.GoBack) {
            onDismiss()
            viewModel.resetLabels()
        }
    }
    val message = when (val current = state) {
        is ReportImageState.Failed -> current.message
        is ReportImageState.Success -> current.message
        else -> stringResource(Res.string.image_report_message)
    }
    WaifuDialog(
        title = stringResource(Res.string.report_title), message = message,
        confirmLabel = stringResource(Res.string.allow), dismissLabel = stringResource(Res.string.cancel),
        loading = state is ReportImageState.Loading,
        onConfirm = { viewModel.onClickAllow(imageId) }, onDismiss = viewModel::onClickCancel
    )
}
