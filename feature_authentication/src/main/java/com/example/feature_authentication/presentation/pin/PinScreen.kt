package com.example.feature_authentication.presentation.pin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_api.utils.extension.collectAsStateLifecycleAware
import com.example.feature_authentication.R
import com.example.feature_authentication.presentation.pin.model.PinUiState
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.lib_ui.components.keyboard.PinKeyboard
import com.example.lib_ui.components.keyboard.model.PinKey
import com.example.lib_ui.components.nav_bar.AppNavBar
import com.example.lib_ui.components.nav_bar.AppNavBarState
import com.example.lib_ui.components.pincode.PinCodeField
import com.example.lib_ui.components.pincode.model.PinCodeFieldItemIndex
import com.example.lib_ui.components.pincode.model.PinCodeFieldState
import com.example.lib_ui.components.pincode.model.PinCodeFieldType
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.theme.AppThemeContainer
import com.example.lib_ui.theme.typography.ViewScale
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
internal fun PinScreen(viewModel: PinViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    BackHandler {
        viewModel.onEvent(PinEvent.Ui.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            AppNavBar(
                state = AppNavBarState(
                    rightPart = null,
                    leftPart = null,
                    middlePart = AppNavBarState.MiddlePart(
                        title = stringResource(id = R.string.authentication_pin_title)
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
                onKeyboardClick = { key -> viewModel.onEvent(PinEvent.Ui.OnKeyboardClick(key)) },
                onLogoutClick = { viewModel.onEvent(PinEvent.Ui.OnLogoutClick) },
            )
        }
    }
}

@Composable
private fun Content(
    state: PinUiState,
    onKeyboardClick: (PinKey) -> Unit,
    onLogoutClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            PinCodeField(
                state = state.pinCodeFieldState,
            )
        }

        PinKeyboard(
            withBiometrics = state.withBiometrics,
            onClick = onKeyboardClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LogoutButton(onClick = onLogoutClick)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun LogoutButton(
    onClick: () -> Unit,
) {
    Text(
        text = stringResource(id = R.string.authentication_pin_logout_button),
        color = AppTheme.palette.text.secondary,
        style = AppTheme.typography.text1Regular,
        modifier = Modifier.clickable { onClick() }
    )
}

@Preview
@Composable
private fun ContentPreview() {
    AppThemeContainer(viewScale = ViewScale.M) {
        Content(
            state = PinUiState(
                pinCodeFieldState = PinCodeFieldState(
                    title = "Title",
                    maxIndex = PinCodeFieldItemIndex.THREE,
                    type = PinCodeFieldType.Default(
                        selectedIndex = PinCodeFieldItemIndex.THREE,
                    ),
                ),
                withBiometrics = true,
            ),
            onKeyboardClick = {},
            onLogoutClick = {},
        )
    }
}
