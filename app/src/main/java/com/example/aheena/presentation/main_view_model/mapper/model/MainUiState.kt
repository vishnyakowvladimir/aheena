package com.example.aheena.presentation.main_view_model.mapper.model

import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale

internal data class MainUiState(
    val themeState: ThemeState?,
) {
    data class ThemeState(
        val themeMode: AppThemeMode,
        val viewScale: ViewScale,
    )
}
