package com.example.core_api.network

import okhttp3.OkHttpClient

interface OkHttpClientBuilderFactory {

    fun createBuilder(): OkHttpClient.Builder
}
