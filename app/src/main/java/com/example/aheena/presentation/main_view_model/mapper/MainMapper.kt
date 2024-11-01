package com.example.aheena.presentation.main_view_model.mapper

import com.example.aheena.presentation.main_view_model.model.MainUiState
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.core.controller.theme.mapToUi
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
