package com.example.aheena.presentation.main_view_model.mvi.model

import com.example.domain_models.theme.ViewScaleDomain

internal data class MainDomainState(
    val themeState: ThemeState,
    val data: String = "",
) {
    data class ThemeState(
        val viewScale: ViewScaleDomain,
    )
}
