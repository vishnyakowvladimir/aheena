package com.example.feature_tech.presentation.select_url.mapper

import com.example.core_api.utils.string_provider.StringProvider
import com.example.data_source_api.models.network.UrlsType
import com.example.feature_tech.R
import com.example.feature_tech.presentation.select_url.model.SelectUrlUiState
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlDomainState
import javax.inject.Inject

internal class SelectUrlMapper @Inject constructor(
    private val stringProvider: StringProvider,
) {

    fun map(domainState: SelectUrlDomainState): SelectUrlUiState {
        return SelectUrlUiState(
            urlsTypeDataList = createUrlsTypeDataList(domainState),
        )
    }

    private fun createUrlsTypeDataList(domainState: SelectUrlDomainState): List<SelectUrlUiState.UrlsTypeData> {
        return UrlsType.entries.map { type ->
            SelectUrlUiState.UrlsTypeData(
                urlsType = type,
                title = getUrlsTypeTitle(type),
                isSelected = type == domainState.urlsType,
            )
        }
    }

    private fun getUrlsTypeTitle(urlsType: UrlsType): String {
        return when (urlsType) {
            UrlsType.PROD -> stringProvider.getString(R.string.tech_select_url_type_prod_title)
            UrlsType.TEST -> stringProvider.getString(R.string.tech_select_url_type_test_title)
            UrlsType.INCORRECT -> stringProvider.getString(R.string.tech_select_url_type_incorrect_title)
        }
    }
}
