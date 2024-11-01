package com.example.data_source_api.storage.theme

import com.example.data_source_api.models.theme.AppThemeModeDto
import com.example.data_source_api.models.theme.ViewScaleDto

interface ThemeStorage {

    fun saveViewScale(scale: ViewScaleDto)

    fun getViewScale(): ViewScaleDto

    fun saveThemeMode(mode: AppThemeModeDto)

    fun getThemeMode(): AppThemeModeDto
}
