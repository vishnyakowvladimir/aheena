package com.example.splash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lib_ui.theme.AppTheme

@Composable
internal fun Splash() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.palette.background.primary),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Hello",
            style = AppTheme.typography.title1Bold,
            color = AppTheme.palette.text.primary,
        )
    }
}

@Preview
@Composable
private fun SplashPreview() {
    Splash()
}
