package com.example.core_impl.network.interceptor

import com.example.core.network.BaseHostInterceptor
import com.example.data_source_api.storage.network.UrlProvider

class ItunesHostInterceptor(
    private val urlProvider: UrlProvider,
) : BaseHostInterceptor() {

    override fun getHost(): String {
        return urlProvider.currentUrls.itunesUrl
    }
}
