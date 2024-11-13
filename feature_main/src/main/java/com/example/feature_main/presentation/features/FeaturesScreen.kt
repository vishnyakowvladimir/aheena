package com.example.feature_main.presentation.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_api.utils.extension.collectAsStateLifecycleAware
import com.example.feature_main.presentation.features.model.FeatureScreen
import com.example.feature_main.presentation.features.mvi.model.FeaturesEvent
import com.example.lib_ui.theme.AppTheme

@Composable
internal fun FeaturesScreen(viewModel: FeaturesViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        state.value.featuresScreens.forEachIndexed { index, feature ->

            Cell(
                feature = feature,
                onClick = { viewModel.onEvent(FeaturesEvent.Ui.OnItemClick(feature)) },
            )

            if (index <= state.value.featuresScreens.size - 2) {
                Divider()
            }
        }
    }
}

@Composable
private fun Cell(
    feature: FeatureScreen,
    onClick: (FeatureScreen) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(feature) },
    ) {
        Text(
            text = feature.title,
            color = AppTheme.palette.text.primary,
            style = AppTheme.typography.text1Regular,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = feature.description,
            color = AppTheme.palette.text.secondary,
            style = AppTheme.typography.text1Regular,
        )
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth(),
    )
}
