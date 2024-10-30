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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.feature_authentication.R
import com.example.feature_authentication.presentation.biometric.model.BiometricsUiState
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.lib_ui.components.button.AppButton
import com.example.lib_ui.components.button.AppButtonState
import com.example.lib_ui.components.nav_bar.AppNavBar
import com.example.lib_ui.components.nav_bar.AppNavBarState
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.utils.SetSystemBarsColor
import com.example.lib_ui.R as LibUiR

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
                    leftPart = AppNavBarState.LeftPart(
                        iconRes = LibUiR.drawable.ic_24dp_navigation_back,
                        onClick = { viewModel.onEvent(BiometricsEvent.Ui.OnBackPressed) },
                    ),
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
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        AppButton(
            state = AppButtonState(
                title = stringResource(id = R.string.authentication_biometrics_button_enable),
            ),
            onClick = onEnableClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppButton(
            state = AppButtonState(
                title = stringResource(id = R.string.authentication_biometrics_button_skip),
            ),
            onClick = onSkipClick,
        )
    }
}
