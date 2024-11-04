package com.example.splash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
internal fun Splash() {
    SetSystemBarsColor(
        statusBarColor = Color.White,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "HELLO :)",
            style = TextStyle(
                fontSize = 24.sp,
            ),
            color = Color.DarkGray,
        )
    }
}

@Preview
@Composable
private fun SplashPreview() {
    Splash()
}
