package com.example.aheena.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aheena.presentation.LocalNavController
import com.example.core.navigation.feature.AuthenticationFeature

@Composable
fun Test1() {
    val navController = LocalNavController.current

    Text(
        text = "Go",
        modifier = Modifier.clickable {
            navController.navigate(AuthenticationFeature())
        }
    )
}

