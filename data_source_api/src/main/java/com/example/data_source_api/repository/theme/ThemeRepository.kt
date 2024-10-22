package com.example.data_source_api.repository.theme

import com.example.domain_models.theme.ViewScaleDomain

interface ThemeRepository {

    fun saveScale(scale: ViewScaleDomain)

    fun getScale(): ViewScaleDomain
}
