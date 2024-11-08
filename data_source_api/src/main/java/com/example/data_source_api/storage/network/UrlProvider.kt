package com.example.data_source_api.storage.network

import com.example.data_source_api.models.network.BaseUrls
import com.example.data_source_api.models.network.UrlsType

interface UrlProvider {
    var selectedUrlsType: UrlsType
    val currentUrls: BaseUrls
}
