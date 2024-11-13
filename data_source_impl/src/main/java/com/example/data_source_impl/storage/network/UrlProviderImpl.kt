package com.example.data_source_impl.storage.network

import androidx.core.content.edit
import com.example.core_api.application_info.ApplicationInfo
import com.example.core_api.utils.shared_preferences.AndroidPreferencesProvider
import com.example.data_source_api.models.network.BaseUrls
import com.example.data_source_api.models.network.UrlsType
import com.example.data_source_api.storage.network.UrlProvider

private const val PREF_KEY_URLS_TYPE = "PREF_KEY_URLS_TYPE"

class UrlProviderImpl(
    private val prodUrls: BaseUrls,
    private val testUrls: BaseUrls,
    private val incorrectUrls: BaseUrls,
    private val preferencesProvider: AndroidPreferencesProvider,
    private val applicationInfo: ApplicationInfo,
) : UrlProvider {

    override var selectedUrlsType: UrlsType
        get() = preferencesProvider.prefs.getString(PREF_KEY_URLS_TYPE, "")
            .orEmpty()
            .ifEmpty {
                if (applicationInfo.isDebug) {
                    UrlsType.TEST.name
                } else {
                    UrlsType.PROD.name
                }
            }
            .let(UrlsType::valueOf)
        set(value) = preferencesProvider.prefs.edit { putString(PREF_KEY_URLS_TYPE, value.name) }

    override val currentUrls: BaseUrls
        get() {
            return when (selectedUrlsType) {
                UrlsType.TEST -> testUrls
                UrlsType.PROD -> prodUrls
                UrlsType.INCORRECT -> incorrectUrls
            }
        }
}
