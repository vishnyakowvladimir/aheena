package com.example.feature_main.presentation.settings.mapper

import com.example.domain_models.theme.AppThemeModeDomain
import com.example.feature_main.presentation.settings.model.SettingsUiState
import com.example.feature_main.presentation.settings.mvi.model.SettingsDomainState
import com.example.lib_ui.theme.AppThemeMode
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class SettingsMapper @Inject constructor() {

    fun map(domainState: SettingsDomainState): SettingsUiState {
        return SettingsUiState(
            themeRadioButtonsState = mapThemeRadioButtonsState(domainState.themeRadioButtonsState),
        )
    }

    private fun mapThemeRadioButtonsState(
        domainState: SettingsDomainState.ThemeRadioButtonsState,
    ): SettingsUiState.ThemeRadioButtonsState {
        return SettingsUiState.ThemeRadioButtonsState(
            buttons = domainState.buttons.map(::mapThemeRadioButtonState).toImmutableList(),
        )
    }

    private fun mapThemeRadioButtonState(
        domainState: SettingsDomainState.ThemeRadioButtonsState.RadioButtonState,
    ): SettingsUiState.ThemeRadioButtonsState.RadioButtonState {
        return SettingsUiState.ThemeRadioButtonsState.RadioButtonState(
            title = domainState.title,
            isSelected = domainState.isSelected,
            mode = when (domainState.mode) {
                AppThemeModeDomain.DARK -> AppThemeMode.DARK
                AppThemeModeDomain.LIGHT -> AppThemeMode.LIGHT
                AppThemeModeDomain.SYSTEM -> AppThemeMode.SYSTEM
            },
        )
    }
}
