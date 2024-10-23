package com.example.data_source_impl.mapper.theme

import com.example.data_source_api.models.theme.AppThemeModeDto
import com.example.data_source_api.models.theme.ViewScaleDto
import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain
import javax.inject.Inject

class ThemeMapper @Inject constructor() {

    fun mapScaleDomainToDto(scale: ViewScaleDomain): ViewScaleDto {
        return when (scale) {
            ViewScaleDomain.M -> ViewScaleDto.M
            ViewScaleDomain.L -> ViewScaleDto.L
            ViewScaleDomain.XL -> ViewScaleDto.XL
        }
    }

    fun mapScaleDtoToDomain(scale: ViewScaleDto): ViewScaleDomain {
        return when (scale) {
            ViewScaleDto.M -> ViewScaleDomain.M
            ViewScaleDto.L -> ViewScaleDomain.L
            ViewScaleDto.XL -> ViewScaleDomain.XL
        }
    }

    fun mapThemeModeDomainToDto(themeMode: AppThemeModeDomain): AppThemeModeDto {
        return when (themeMode) {
            AppThemeModeDomain.SYSTEM -> AppThemeModeDto.SYSTEM
            AppThemeModeDomain.LIGHT -> AppThemeModeDto.LIGHT
            AppThemeModeDomain.DARK -> AppThemeModeDto.DARK
        }
    }

    fun mapThemeModeDtoToDomain(themeMode: AppThemeModeDto): AppThemeModeDomain {
        return when (themeMode) {
            AppThemeModeDto.SYSTEM -> AppThemeModeDomain.SYSTEM
            AppThemeModeDto.LIGHT -> AppThemeModeDomain.LIGHT
            AppThemeModeDto.DARK -> AppThemeModeDomain.DARK
        }
    }
}
