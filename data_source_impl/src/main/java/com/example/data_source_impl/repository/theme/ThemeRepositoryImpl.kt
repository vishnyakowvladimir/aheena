package com.example.data_source_impl.repository.theme

import com.example.data_source_api.repository.theme.ThemeRepository
import com.example.data_source_api.source.theme.ThemeSource
import com.example.data_source_impl.mapper.theme.ThemeMapper
import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val themeSource: ThemeSource,
    private val themeMapper: ThemeMapper,
) : ThemeRepository {

    override fun saveScale(scale: ViewScaleDomain) {
        themeSource.saveViewScale(themeMapper.mapScaleDomainToDto(scale))
    }

    override fun getScale(): ViewScaleDomain {
        return themeMapper.mapScaleDtoToDomain(themeSource.getViewScale())
    }

    override fun saveThemeMode(themeMode: AppThemeModeDomain) {
        themeSource.saveThemeMode(themeMapper.mapThemeModeDomainToDto(themeMode))
    }

    override fun getThemeMode(): AppThemeModeDomain {
        return themeMapper.mapThemeModeDtoToDomain(themeSource.getThemeMode())
    }
}
