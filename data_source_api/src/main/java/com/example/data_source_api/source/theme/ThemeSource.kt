package com.example.data_source_api.source.theme

import com.example.data_source_api.models.theme.ViewScaleDto

interface ThemeSource {

    fun saveViewScale(scale: ViewScaleDto)

    fun getViewScale(): ViewScaleDto
}
