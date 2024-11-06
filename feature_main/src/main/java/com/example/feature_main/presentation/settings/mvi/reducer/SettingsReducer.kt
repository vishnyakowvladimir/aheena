package com.example.feature_main.presentation.settings.mvi.reducer

import com.example.feature_main.presentation.settings.mvi.model.SettingsDomainState
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.model.SettingsSideEffect
import com.example.feature_main.presentation.settings.mvi.model.SettingsUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class SettingsReducer @Inject constructor(
    private val uiReducer: SettingsUiReducer,
    private val domainReducer: SettingsDomainReducer,
) : Reducer<SettingsEvent, SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {

    override fun update(
        state: SettingsDomainState,
        event: SettingsEvent,
    ): Update<SettingsDomainState, SettingsSideEffect, SettingsUiCommand> {
        return when (event) {
            is SettingsEvent.None -> Update.nothing()
            is SettingsEvent.Ui -> uiReducer.update(state, event)
            is SettingsEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): SettingsDomainState {
        return SettingsDomainState()
    }
}
