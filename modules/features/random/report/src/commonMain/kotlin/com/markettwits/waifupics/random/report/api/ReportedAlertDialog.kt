package com.markettwits.waifupics.random.report.api

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.markettwits.theme.components.FontRubik
import com.markettwits.theme.components.LightPink
import com.markettwits.theme.components.PurpleGrey40
import com.markettwits.theme.components.Shapes
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.report.generated.resources.Res
import waifupics.modules.features.random.report.generated.resources.allow
import waifupics.modules.features.random.report.generated.resources.cancel
import waifupics.modules.features.random.report.generated.resources.image_report_message
import waifupics.modules.features.random.report.generated.resources.report_title


@Composable
fun ReportedAlertDialog(
    imageId : Int,
    viewModel: ReportImageViewModel,
    onDismiss: () -> Unit
) {

    val state = viewModel.state.collectAsState()

    val labels = viewModel.labels.collectAsState()

    when (labels.value) {
        ReportImageViewModel.Labels.Empty -> {}
        ReportImageViewModel.Labels.GoBack -> {
            onDismiss()
            viewModel.resetLabels()
        }
    }

    Dialog(onDismissRequest = { viewModel.onClickCancel() }) {

        when (val value = state.value) {
            is ReportImageState.Base -> {
                ProtectedAlertDialogLayout(
                    isBase = true,
                    isLoading = false,
                    onSubmit = {
                        viewModel.onClickAllow(imageId)
                    },
                    onDismiss = viewModel::onClickCancel
                )
            }

            is ReportImageState.Failed -> {
                ProtectedAlertDialogLayout(
                    isBase = false,
                    isLoading = false,
                    message = value.message,
                    onSubmit = {
                        viewModel.onClickAllow(imageId)
                    },
                    onDismiss = viewModel::onClickCancel
                )
            }

            is ReportImageState.Loading -> {
                ProtectedAlertDialogLayout(
                    isBase = false,
                    isLoading = true,
                    onSubmit = {
                        viewModel.onClickAllow(imageId)
                    },
                    onDismiss = viewModel::onClickCancel
                )
            }

            is ReportImageState.Success -> {
                ProtectedAlertDialogLayout(
                    isBase = false,
                    isLoading = false,
                    message = value.message,
                    onSubmit = {
                        viewModel.onClickAllow(imageId)
                    },
                    onDismiss = viewModel::onClickCancel
                )
            }
        }
    }
}

@Composable
private fun ProtectedAlertDialogLayout(
    modifier: Modifier = Modifier,
    isBase: Boolean,
    isLoading: Boolean,
    message: String = "",
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    Card(
        shape = Shapes.medium,
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier.background(MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontSize = 16.sp,
                    text = stringResource(Res.string.report_title),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontRubik.bold()
                )
                val currentMessage =
                    if (isBase) stringResource(Res.string.image_report_message) else message
                if (isLoading) {
                    CircularProgressIndicator(color = LightPink)
                } else {
                    Text(
                        fontSize = 14.sp,
                        text = currentMessage,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = FontRubik.medium()
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(LightPink),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(
                        text = stringResource(Res.string.cancel),
                        fontWeight = FontWeight.Bold,
                        color = PurpleGrey40,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                    if (!isLoading) {
                        onSubmit()
                    }
                }) {
                    Text(
                        text = stringResource(Res.string.allow),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}