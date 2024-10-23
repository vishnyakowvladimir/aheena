package com.example.core.presentation.theme_manager

import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale

interface ThemeManager {
    fun applyThemeMode(themeMode: Int)

    fun saveScale(scale: ViewScale)

    fun saveScale(scale: ViewScaleDomain)

    fun getScale(): ViewScale

    fun saveThemeMode(themeMode: AppThemeMode)

    fun saveThemeMode(themeMode: AppThemeModeDomain)

    fun getThemeMode(): AppThemeMode
}
