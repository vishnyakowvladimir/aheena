package com.example.core.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

abstract class BaseHostInterceptor : Interceptor {

    protected abstract fun getHost(): String

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl = request.url
            .toString()
            .replace(RetrofitBuilderFactory.BASE_URL, getHost())

        val newRequest = request
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
