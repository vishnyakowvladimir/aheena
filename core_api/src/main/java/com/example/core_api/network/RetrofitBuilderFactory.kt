package com.example.core_api.network

import retrofit2.Retrofit

interface RetrofitBuilderFactory {

    companion object {
        const val BASE_URL = "https://127.0.0.1"
    }

    fun createBuilder(): Retrofit.Builder
}
