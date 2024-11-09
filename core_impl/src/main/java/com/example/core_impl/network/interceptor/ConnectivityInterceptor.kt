package com.example.core_impl.network.interceptor

import com.example.core.network.model.BaseApiException
import com.example.core.utils.connectivity_checker.ConnectivityChecker
import com.example.core.utils.eventbus.AppEventBus
import com.example.core.utils.eventbus.model.AppEvent
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
    private val connectivityChecker: ConnectivityChecker,
    private val eventBus: AppEventBus,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityChecker.isNetworkAvailable()) {
            eventBus.sendEvent(AppEvent.OnNoInternetConnection)
            throw BaseApiException.ConnectivityException()
        }

        val httpUrlBuilder = chain
            .request()
            .url
            .newBuilder()

        val httpUrl = httpUrlBuilder.build()
        return chain.proceed(chain.request().newBuilder().url(httpUrl).build())
    }
}
