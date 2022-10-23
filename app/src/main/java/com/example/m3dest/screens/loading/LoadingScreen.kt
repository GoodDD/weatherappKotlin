package com.example.m3dest.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.Bold,
                            fontStyle = MaterialTheme.typography.headlineLarge.fontStyle

                        )
                    ) {
                        withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
                            append("We are collecting\n")
                            append("forecast data for you")
                        }
                        withStyle(ParagraphStyle(textAlign = TextAlign.Right)) {
                            append("please wait...")
                        }
                    }
                }
                /*text = "We are collecting forecast data for you please wait...",
                softWrap = false,
                maxLines = 3,
                style = MaterialTheme.typography.titleLarge*/
            )
            LinearProgressIndicator()
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen()
}