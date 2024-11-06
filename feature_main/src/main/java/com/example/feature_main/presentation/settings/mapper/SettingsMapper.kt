package com.example.feature_main.presentation.settings.mapper

import com.example.feature_main.presentation.settings.model.SettingsUiState
import com.example.feature_main.presentation.settings.mvi.model.SettingsDomainState
import javax.inject.Inject

internal class SettingsMapper @Inject constructor() {

    fun map(domainState: SettingsDomainState): SettingsUiState {
        return SettingsUiState(
            isLoading = domainState.isLoading,
        )
    }
}
