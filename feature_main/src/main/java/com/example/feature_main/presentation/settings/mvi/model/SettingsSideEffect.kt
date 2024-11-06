package com.example.feature_main.presentation.settings.mvi.model

internal sealed interface SettingsSideEffect {

    sealed interface Ui : SettingsSideEffect {
        data object Logout : Ui
        data object ApplyThemeLight : Ui
        data object ApplyThemeDark : Ui
        data object ApplyThemeSystem : Ui
    }

    sealed interface Domain : SettingsSideEffect
}


