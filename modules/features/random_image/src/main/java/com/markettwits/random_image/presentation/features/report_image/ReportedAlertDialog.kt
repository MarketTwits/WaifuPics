package com.markettwits.random_image.presentation.features.report_image


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.markettwits.core_ui.R
import com.markettwits.core_ui.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.theame.theme.LightPink
import com.markettwits.waifupics.theame.theme.PurpleGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ReportedAlertDialogPreview(){
    WaifuPicsTheme(darkTheme = true) {
        ReportedAlertDialog(onSubmit = {}, onDismiss = {})
    }
}
@Preview
@Composable
private fun ReportedAlertDialogPreviewLigh(){
    WaifuPicsTheme(darkTheme = false) {
        ReportedAlertDialog(onSubmit = {}, onDismiss = {})
    }
}

@Composable
fun ReportedAlertDialog(onSubmit : () -> Unit, onDismiss : () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        ProtectedAlertDialogLayout(onSubmit = {onSubmit()}, onDismiss = {onDismiss()})
    }
}

@Composable
private fun ProtectedAlertDialogLayout(modifier: Modifier = Modifier, onSubmit : () -> Unit, onDismiss : () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier
                .background(MaterialTheme.colorScheme.secondary)
        ) {

            //.......................................................................
            Image(
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = LightPink
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = "Report",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                androidx.compose.material3.Text(
                    text = "Do you think that this image does not correspond to this category ?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(LightPink),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                androidx.compose.material3.TextButton(onClick = {
                    onDismiss()
                }) {

                    androidx.compose.material3.Text(
                        "Cancel",
                        fontWeight = FontWeight.Bold,
                        color = PurpleGrey40,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                androidx.compose.material3.TextButton(onClick = {
                    onSubmit()
                }) {
                    androidx.compose.material3.Text(
                        "Allow",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}