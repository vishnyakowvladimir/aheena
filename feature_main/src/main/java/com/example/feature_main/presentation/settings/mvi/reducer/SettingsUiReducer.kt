package com.example.feature_main.presentation.settings.mvi.reducer

import com.example.domain_models.theme.AppThemeModeDomain
import com.example.feature_main.presentation.settings.mvi.model.SettingsDomainState
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.model.SettingsSideEffect
import com.example.feature_main.presentation.settings.mvi.model.SettingsUiCommand
import com.example.lib_ui.theme.AppThemeMode
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class SettingsUiReducer @Inject constructor() :
    Reducer<SettingsEvent.Ui, SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {

    override fun update(
        state: SettingsDomainState,
        event: SettingsEvent.Ui,
    ): Update<SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {
        return when (event) {
            is SettingsEvent.Ui.OnLogoutClick -> reduceOnLogoutClick()
            is SettingsEvent.Ui.OnThemeSelected -> reduceOnThemeSelected(state, event)
        }
    }

    private fun reduceOnLogoutClick(): Update<SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {
        return Update.sideEffects(listOf(SettingsSideEffect.Ui.Logout))
    }

    private fun reduceOnThemeSelected(
        state: SettingsDomainState,
        event: SettingsEvent.Ui.OnThemeSelected,
    ): Update<SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {
        val selectedThemeMode = when(event.themeMode) {
            AppThemeMode.DARK -> AppThemeModeDomain.DARK
            AppThemeMode.LIGHT -> AppThemeModeDomain.LIGHT
            AppThemeMode.SYSTEM -> AppThemeModeDomain.SYSTEM
        }

        val currentThemeRadioButtonsState = state.themeRadioButtonsState
        val updatedThemeRadioButtonsState = currentThemeRadioButtonsState.copy(
            buttons = currentThemeRadioButtonsState.buttons.map { buttonState ->
                buttonState.copy(isSelected = buttonState.mode == selectedThemeMode)
            },
        )

        val sideEffect = when(event.themeMode) {
            AppThemeMode.LIGHT -> SettingsSideEffect.Ui.ApplyThemeLight
            AppThemeMode.DARK -> SettingsSideEffect.Ui.ApplyThemeDark
            AppThemeMode.SYSTEM -> SettingsSideEffect.Ui.ApplyThemeSystem
        }

        return Update.stateWithSideEffects(
            state = state.copy(
                themeRadioButtonsState = updatedThemeRadioButtonsState,
            ),
            sideEffects = listOf(sideEffect),
        )
    }
}
