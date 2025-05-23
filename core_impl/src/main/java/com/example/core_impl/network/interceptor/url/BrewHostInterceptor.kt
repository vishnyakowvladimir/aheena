package com.example.core_impl.network.interceptor.url

import com.example.core_api.network.BaseHostInterceptor
import com.example.data_source_api.storage.network.UrlProvider

class BrewHostInterceptor(
    private val urlProvider: UrlProvider,
) : BaseHostInterceptor() {

    override fun getHost(): String {
        return urlProvider.currentUrls.brewUrl
    }
}
