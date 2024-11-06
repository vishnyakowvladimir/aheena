package com.example.feature_main.presentation.settings.mvi.reducer

import com.example.feature_main.presentation.settings.mvi.model.SettingsDomainState
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.model.SettingsSideEffect
import com.example.feature_main.presentation.settings.mvi.model.SettingsUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class SettingsDomainReducer @Inject constructor() :
    Reducer<SettingsEvent.Domain, SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {

    override fun update(
        state: SettingsDomainState,
        event: SettingsEvent.Domain,
    ): Update<SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {
        return Update.nothing()
    }
}

