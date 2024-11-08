package com.example.core.network

import okhttp3.OkHttpClient

interface OkHttpClientBuilderFactory {

    fun create(): OkHttpClient.Builder
}
