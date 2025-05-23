package com.example.aheena.presentation.main_view_model.model

import com.example.lib_ui.theme.typography.ViewScale

internal data class MainUiState(
    val themeState: ThemeState,
) {
    data class ThemeState(
        val viewScale: ViewScale,
    )
}
