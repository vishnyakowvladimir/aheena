package com.example.data_source_impl.mapper.theme

import com.example.data_source_api.models.theme.ViewScaleDto
import com.example.domain_models.theme.ViewScaleDomain
import javax.inject.Inject

class ThemeMapper @Inject constructor() {

    fun mapScaleDomainToDto(scale: ViewScaleDomain): ViewScaleDto {
        return when (scale) {
            ViewScaleDomain.M -> ViewScaleDto.M
            ViewScaleDomain.L -> ViewScaleDto.L
            ViewScaleDomain.XL -> ViewScaleDto.L
        }
    }

    fun mapScaleDtoToDomain(scale: ViewScaleDto): ViewScaleDomain {
        return when (scale) {
            ViewScaleDto.M -> ViewScaleDomain.M
            ViewScaleDto.L -> ViewScaleDomain.L
            ViewScaleDto.XL -> ViewScaleDomain.L
        }
    }
}
