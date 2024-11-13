package com.example.feature_main.presentation.settings.mvi.reducer

import com.example.core_api.controller.theme.ThemeManager
import com.example.core_api.utils.string_provider.StringProvider
import com.example.domain_models.theme.AppThemeModeDomain
import com.example.feature_main.R
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
    private val themeManager: ThemeManager,
    private val stringProvider: StringProvider,
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
        val currentThemeMode = themeManager.getThemeMode()

        return SettingsDomainState(
            themeRadioButtonsState = SettingsDomainState.ThemeRadioButtonsState(
                buttons = listOf(
                    SettingsDomainState.ThemeRadioButtonsState.RadioButtonState(
                        mode = AppThemeModeDomain.LIGHT,
                        title = stringProvider.getString(R.string.settings_theme_mode_light),
                        isSelected = currentThemeMode == AppThemeModeDomain.LIGHT,
                    ),
                    SettingsDomainState.ThemeRadioButtonsState.RadioButtonState(
                        mode = AppThemeModeDomain.DARK,
                        title = stringProvider.getString(R.string.settings_theme_mode_dark),
                        isSelected = currentThemeMode == AppThemeModeDomain.DARK,
                    ),
                    SettingsDomainState.ThemeRadioButtonsState.RadioButtonState(
                        mode = AppThemeModeDomain.SYSTEM,
                        title = stringProvider.getString(R.string.settings_theme_mode_system),
                        isSelected = currentThemeMode == AppThemeModeDomain.SYSTEM,
                    ),
                )
            )
        )
    }
}
