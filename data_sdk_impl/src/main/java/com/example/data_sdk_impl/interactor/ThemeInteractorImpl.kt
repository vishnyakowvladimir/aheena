package com.example.data_sdk_impl.interactor

import com.example.data_sdk_api.interactor.ThemeInteractor
import com.example.data_source_api.repository.theme.ThemeRepository
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
}
