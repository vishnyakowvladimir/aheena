package com.example.data_source_api.repository.theme

import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain

interface ThemeRepository {
    fun saveScale(scale: ViewScaleDomain)
    fun getScale(): ViewScaleDomain
    fun saveThemeMode(themeMode: AppThemeModeDomain)
    fun getThemeMode(): AppThemeModeDomain
}
