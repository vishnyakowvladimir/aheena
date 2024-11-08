package com.example.core_impl.network.interceptor

import com.example.core.network.model.BaseNetworkException
import com.example.core.utils.connectivity_checker.ConnectivityChecker
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
    private val connectivityChecker: ConnectivityChecker,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityChecker.isNetworkAvailable()) {
            throw BaseNetworkException.ConnectivityException()
        }

        val httpUrlBuilder = chain
            .request()
            .url
            .newBuilder()

        val httpUrl = httpUrlBuilder.build()
        return chain.proceed(chain.request().newBuilder().url(httpUrl).build())
    }
}
