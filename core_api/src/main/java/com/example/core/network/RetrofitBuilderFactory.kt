package com.example.core.network

import retrofit2.Retrofit

interface RetrofitBuilderFactory {

    companion object {
        const val BASE_URL = "127.0.0.1"
    }

    fun create(): Retrofit.Builder
}
