package com.markettwits.presentation.detail.info

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun InfoImageContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        letterSpacing = MaterialTheme.typography.titleLarge.letterSpacing
                    )
                ) {
                    append("Metadata")
                }
                append("\n")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing
                    )
                ) {
                    append("beta")
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(8.dp))
        }

        TextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = "labale",
            onValueChange = { newValue ->
            },
            label = {
                Text(text = "label")
            },
            singleLine = true,
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        TextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(112.dp),
            value = "Image description",
            onValueChange = { "on Value changed" },
            label = {
                "description"
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        Row(
            modifier = Modifier.padding(24.dp),
            horizontalArrangement = Arrangement
                .spacedBy(24.dp, Alignment.CenterHorizontally)
        ) {
            val tertiaryContainer = MaterialTheme.colorScheme.tertiaryContainer
            val tertiaryOnContainer = MaterialTheme.colorScheme.onTertiaryContainer
            Button(
                onClick = {
                    //todo hide
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = tertiaryContainer,
                    contentColor = tertiaryOnContainer
                )
            ) {
                Text(text = "cancel")
            }
            Button(
                onClick = {
//                    scope.launch(Dispatchers.Main) {
//                        if (shouldRemoveMetadata) {
//                            exifAttributes = ExifAttributes()
//                        } else if (shouldRemoveLocation) {
//                            exifAttributes = exifAttributes.copy(gpsLatLong = null)
//                        }
//                        request.launch(media.writeRequest(cr))
//                    }
                }
            ) {
                Text(text = "action confirm")
            }
        }
    }
}