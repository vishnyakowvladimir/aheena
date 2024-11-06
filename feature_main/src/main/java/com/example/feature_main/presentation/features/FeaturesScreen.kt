package com.example.feature_main.presentation.features

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.core.utils.extension.collectAsStateLifecycleAware

@Composable
internal fun FeaturesScreen(viewModel: FeaturesViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()
    Text(text = "Features")
}
