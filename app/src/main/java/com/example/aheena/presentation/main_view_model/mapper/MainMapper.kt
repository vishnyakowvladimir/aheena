package com.example.aheena.presentation.main_view_model.mapper

import com.example.aheena.presentation.main_view_model.mapper.model.MainUiState
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import javax.inject.Inject

internal class MainMapper @Inject constructor() {

    fun map(domainState: MainDomainState): MainUiState {
        return MainUiState(
            themeState = mapThemeState(domainState.themeState),
        )
    }

    private fun mapThemeState(state: MainDomainState.ThemeState?): MainUiState.ThemeState? {
        if (state == null) {
            return null
        }

        return MainUiState.ThemeState(
            themeMode = state.themeMode,
            viewScale = state.viewScale,
        )
    }
}
