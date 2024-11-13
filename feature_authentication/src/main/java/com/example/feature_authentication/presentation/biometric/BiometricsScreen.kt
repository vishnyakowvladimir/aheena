package com.example.feature_authentication.presentation.biometric

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_api.utils.extension.collectAsStateLifecycleAware
import com.example.feature_authentication.R
import com.example.feature_authentication.presentation.biometric.model.BiometricsUiState
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.lib_ui.components.button.AppButton
import com.example.lib_ui.components.button.AppButtonState
import com.example.lib_ui.components.loader.GradientCircularLoader
import com.example.lib_ui.components.nav_bar.AppNavBar
import com.example.lib_ui.components.nav_bar.AppNavBarState
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
internal fun BiometricsScreen(viewModel: BiometricsViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    BackHandler {
        viewModel.onEvent(BiometricsEvent.Ui.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            AppNavBar(
                state = AppNavBarState(
                    rightPart = null,
                    leftPart = null,
                    middlePart = AppNavBarState.MiddlePart(
                        title = stringResource(id = R.string.authentication_biometrics_title)
                    ),
                    backgroundColor = AppTheme.palette.background.primary,
                ),
            )
        },
        containerColor = AppTheme.palette.background.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Content(
                state = state.value,
                onEnableClick = { viewModel.onEvent(BiometricsEvent.Ui.OnEnableButtonClick) },
                onSkipClick = { viewModel.onEvent(BiometricsEvent.Ui.OnSkipButtonClick) },
            )
        }
    }
}

@Composable
private fun Content(
    state: BiometricsUiState,
    onEnableClick: () -> Unit,
    onSkipClick: () -> Unit,
) {
    if (state.isLoading) {
        GradientCircularLoader.XL
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppButton(
            state = AppButtonState(
                title = stringResource(id = R.string.authentication_biometrics_button_enable),
                isEnabled = !state.isLoading,
            ),
            onClick = onEnableClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppButton(
            state = AppButtonState(
                title = stringResource(id = R.string.authentication_biometrics_button_skip),
                isEnabled = !state.isLoading,
            ),
            onClick = onSkipClick,
        )
    }
}
