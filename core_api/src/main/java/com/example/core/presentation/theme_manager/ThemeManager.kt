package com.example.core.presentation.theme_manager

import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale

interface ThemeManager {
    fun applyThemeModeLight()

    fun applyThemeModeDark()

    fun applyThemeModeSystem()

    fun saveScale(scale: ViewScale)

    fun getScale(): ViewScale

    fun getThemeMode(): AppThemeMode
}
