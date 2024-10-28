package com.example.data_sdk_api.interactor.theme

import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain

interface ThemeInteractor {
    fun saveScale(scale: ViewScaleDomain)
    fun getScale(): ViewScaleDomain
    fun saveThemeMode(themeMode: AppThemeModeDomain)
    fun getThemeMode(): AppThemeModeDomain
}
