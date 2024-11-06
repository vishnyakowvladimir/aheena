package com.example.feature_main.presentation.settings.mvi.model

internal sealed interface SettingsEvent {

    data object None : SettingsEvent

    sealed interface Ui : SettingsEvent {
        data object OnLogoutClick : Ui
    }

    sealed interface Domain : SettingsEvent
}
