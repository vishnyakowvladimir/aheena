package com.example.feature_main.presentation.settings.mvi.reducer

import com.example.feature_main.presentation.settings.mvi.model.SettingsDomainState
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.model.SettingsSideEffect
import com.example.feature_main.presentation.settings.mvi.model.SettingsUiCommand
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
        }
    }

    private fun reduceOnLogoutClick(): Update<SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {
        return Update.sideEffects(listOf(SettingsSideEffect.Ui.Logout))
    }
}
