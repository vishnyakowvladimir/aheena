package com.example.data_sdk_api.interactor

import com.example.domain_models.theme.ViewScaleDomain

interface ThemeInteractor {

    fun saveScale(scale: ViewScaleDomain)

    fun getScale(): ViewScaleDomain
}
