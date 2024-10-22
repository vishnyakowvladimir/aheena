package com.example.aheena.presentation.main_view_model.mvi.model

import com.example.domain_models.theme.ViewScaleDomain
import com.example.lib_ui.theme.AppThemeMode

internal data class MainDomainState(
    val themeState: ThemeState? = null,
) {
    data class ThemeState(
        val themeMode: AppThemeMode,
        val viewScale: ViewScaleDomain,
    )
}
