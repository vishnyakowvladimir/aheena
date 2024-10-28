package com.example.data_sdk_impl.interactor.theme

import com.example.data_sdk_api.interactor.theme.ThemeInteractor
import com.example.data_source_api.repository.theme.ThemeRepository
import com.example.domain_models.theme.AppThemeModeDomain
import com.example.domain_models.theme.ViewScaleDomain
import javax.inject.Inject

class ThemeInteractorImpl @Inject constructor(
    private val themeRepository: ThemeRepository,
) : ThemeInteractor {

    override fun saveScale(scale: ViewScaleDomain) {
        themeRepository.saveScale(scale)
    }

    override fun getScale(): ViewScaleDomain {
        return themeRepository.getScale()
    }

    override fun saveThemeMode(themeMode: AppThemeModeDomain) {
        themeRepository.saveThemeMode(themeMode)
    }

    override fun getThemeMode(): AppThemeModeDomain {
        return themeRepository.getThemeMode()
    }
}
