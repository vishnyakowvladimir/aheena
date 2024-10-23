package com.example.feature_authentication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
fun AuthenticationContainer() {
    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.palette.background.primary),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Hello Auth",
            style = AppTheme.typography.title1Bold,
            color = AppTheme.palette.text.accent,
        )
    }

}
