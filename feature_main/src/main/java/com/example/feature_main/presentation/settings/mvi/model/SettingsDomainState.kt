package com.example.feature_main.presentation.settings.mvi.model

import com.example.domain_models.theme.AppThemeModeDomain

internal data class SettingsDomainState(
    val themeRadioButtonsState: ThemeRadioButtonsState,
) {

    data class ThemeRadioButtonsState(
        val buttons: List<RadioButtonState>,
    ) {

        data class RadioButtonState(
            val mode: AppThemeModeDomain,
            val title: String,
            val isSelected: Boolean,
        )
    }
}
