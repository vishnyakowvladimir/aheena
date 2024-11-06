package com.example.feature_main.presentation.settings.model

import com.example.lib_ui.theme.AppThemeMode
import kotlinx.collections.immutable.ImmutableList

internal data class SettingsUiState(
    val themeRadioButtonsState: ThemeRadioButtonsState,
) {
    data class ThemeRadioButtonsState(
        val buttons: ImmutableList<RadioButtonState>,
    ) {

        data class RadioButtonState(
            val mode: AppThemeMode,
            val title: String,
            val isSelected: Boolean,
        )
    }
}
