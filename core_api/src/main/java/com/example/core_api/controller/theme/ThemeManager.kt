package com.example.core_api.controller.theme

import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale

interface ThemeManager {

    fun applyThemeMode()

    fun applyThemeModeLight()

    fun applyThemeModeDark()

    fun applyThemeModeSystem()

    fun saveScale(scale: ViewScaleDomain)

    fun getScale(): ViewScaleDomain

    fun getThemeMode(): AppThemeModeDomain
}

fun ViewScaleDomain.mapToUi(): ViewScale {
    return when (this) {
        ViewScaleDomain.M -> ViewScale.M
        ViewScaleDomain.L -> ViewScale.L
        ViewScaleDomain.XL -> ViewScale.XL
    }
}

fun ViewScale.mapToDomain(): ViewScaleDomain {
    return when (this) {
        ViewScale.M -> ViewScaleDomain.M
        ViewScale.L -> ViewScaleDomain.L
        ViewScale.XL -> ViewScaleDomain.XL
    }
}

fun AppThemeModeDomain.mapToUi(): AppThemeMode {
    return when (this) {
        AppThemeModeDomain.LIGHT -> AppThemeMode.LIGHT
        AppThemeModeDomain.DARK -> AppThemeMode.DARK
        AppThemeModeDomain.SYSTEM -> AppThemeMode.SYSTEM
    }
}

fun AppThemeMode.mapToDomain(): AppThemeModeDomain {
    return when (this) {
        AppThemeMode.LIGHT -> AppThemeModeDomain.LIGHT
        AppThemeMode.DARK -> AppThemeModeDomain.DARK
        AppThemeMode.SYSTEM -> AppThemeModeDomain.SYSTEM
    }
}
