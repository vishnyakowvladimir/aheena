package com.example.aheena.presentation.main_view_model.mapper

import com.example.aheena.presentation.main_view_model.mapper.model.MainUiState
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.domain_models.theme.ViewScaleDomain
import com.example.lib_ui.theme.typography.ViewScale
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
            viewScale = mapScale(state.viewScale),
        )
    }

    private fun mapScale(viewScale: ViewScaleDomain): ViewScale {
        return when(viewScale) {
            ViewScaleDomain.M -> ViewScale.M
            ViewScaleDomain.L -> ViewScale.L
            ViewScaleDomain.XL -> ViewScale.XL
        }
    }
}
