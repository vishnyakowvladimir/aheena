package com.example.feature_tech.presentation.select_url.model

import com.example.data_source_api.models.network.UrlsType

internal data class SelectUrlUiState(
    val urlsTypeDataList: List<UrlsTypeData>,
) {

    data class UrlsTypeData(
        val urlsType: UrlsType,
        val title: String,
        val isSelected: Boolean,
    )
}
