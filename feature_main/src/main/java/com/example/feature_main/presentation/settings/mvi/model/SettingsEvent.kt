package com.example.feature_main.presentation.settings.mvi.model

import com.example.lib_ui.theme.AppThemeMode

internal sealed interface SettingsEvent {

    data object None : SettingsEvent

    sealed interface Ui : SettingsEvent {
        data object OnLogoutClick : Ui
        data class OnThemeSelected(val themeMode: AppThemeMode) : Ui
    }

    sealed interface Domain : SettingsEvent
}
