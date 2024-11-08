package com.example.core.network

import okhttp3.OkHttpClient

interface OkHttpClientBuilderFactory {

    fun createBuilder(): OkHttpClient.Builder
}
