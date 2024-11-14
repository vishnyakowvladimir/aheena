package com.example.core_impl.network.interceptor

import com.example.core_api.network.ExceptionHandler
import com.example.core_api.network.model.BaseApiException
import com.example.core_api.utils.connectivity_checker.ConnectivityChecker
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
    private val connectivityChecker: ConnectivityChecker,
    private val exceptionHandler: ExceptionHandler,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityChecker.isNetworkAvailable()) {
            val connectivityException = BaseApiException.ConnectivityException()
            exceptionHandler.onException(connectivityException)
            throw connectivityException
        }

        val httpUrlBuilder = chain
            .request()
            .url
            .newBuilder()

        val httpUrl = httpUrlBuilder.build()
        return chain.proceed(chain.request().newBuilder().url(httpUrl).build())
    }
}
