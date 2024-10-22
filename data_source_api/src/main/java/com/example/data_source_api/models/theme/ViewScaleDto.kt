package com.example.data_source_api.models.theme

enum class ViewScaleDto(
    val id: Int,
    val textMultiplier: Float,
) {
    M(0, 1f),
    L(1, 1.1f),
    XL(2, 1.3f);
}

