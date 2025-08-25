package com.example.aheena.presentation.main_activity.mapper

import com.example.aheena.presentation.main_activity.model.MainUiState
import com.example.aheena.presentation.main_activity.mvi.model.MainDomainState
import com.example.core_api.controller.theme.mapToUi
import javax.inject.Inject

internal class MainMapper @Inject constructor() {

    fun map(domainState: MainDomainState): MainUiState {
        return MainUiState(
            themeState = mapThemeState(themeState = domainState.themeState),
        )
    }

    private fun mapThemeState(themeState: MainDomainState.ThemeState): MainUiState.ThemeState {
        return MainUiState.ThemeState(
            viewScale = themeState.viewScale.mapToUi(),
        )
    }
}
