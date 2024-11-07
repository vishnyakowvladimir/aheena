package com.example.feature_main.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.feature_main.R
import com.example.feature_main.presentation.settings.model.SettingsUiState
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.theme.AppThemeMode

@Composable
internal fun SettingsScreen(viewModel: SettingsViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            ThemeRadioButtonsBlock(
                state = state.value.themeRadioButtonsState,
                onClick = { themeMode ->
                    viewModel.onEvent(SettingsEvent.Ui.OnThemeSelected(themeMode))
                },
            )
        }

        LogoutBlock(
            onClick = {
                viewModel.onEvent(SettingsEvent.Ui.OnLogoutClick)
            },
        )
    }
}

@Composable
private fun ThemeRadioButtonsBlock(
    state: SettingsUiState.ThemeRadioButtonsState,
    onClick: (AppThemeMode) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Title(text = stringResource(id = R.string.settings_theme_title))

        Spacer(modifier = Modifier.height(4.dp))

        state.buttons.forEach { buttonState ->
            RadioButtonBlock(
                state = buttonState,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun RadioButtonBlock(
    state: SettingsUiState.ThemeRadioButtonsState.RadioButtonState,
    onClick: (AppThemeMode) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = state.isSelected,
                onClick = {
                    onClick(state.mode)
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        RadioButton(
            selected = state.isSelected,
            onClick = { onClick(state.mode) },
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = AppTheme.palette.element.accent,
                unselectedColor = AppTheme.palette.element.primary,
            ),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = state.title,
            style = AppTheme.typography.text1Regular,
            color = AppTheme.palette.text.secondary,
        )

    }
}

@Composable
private fun Title(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.title3Bold,
        color = AppTheme.palette.text.primary,
    )
}

@Composable
private fun LogoutBlock(
    onClick: () -> Unit,
) {
    Text(
        text = stringResource(id = R.string.settings_logout_button),
        style = AppTheme.typography.text1Regular,
        color = AppTheme.palette.text.secondary,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 12.dp),
    )
}

