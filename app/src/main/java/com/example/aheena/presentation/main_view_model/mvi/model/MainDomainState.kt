package com.example.aheena.presentation.main_view_model.mvi.model

import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain

internal data class MainDomainState(
    val themeState: ThemeState? = null,
) {
    data class ThemeState(
        val themeMode: AppThemeModeDomain,
        val viewScale: ViewScaleDomain,
    )
}
