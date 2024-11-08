package com.example.core_impl.network

import android.support.multidex.BuildConfig
import com.example.core.network.InterceptorProvider
import com.example.core_impl.network.interceptor.BrewHostInterceptor
import com.example.core_impl.network.interceptor.ItunesHostInterceptor
import com.example.data_source_api.storage.network.UrlProvider
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class InterceptorProviderImpl @Inject constructor(
    private val urlProvider: UrlProvider,
) : InterceptorProvider {

    override fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
            }
    }

    override fun provideItunesHostInterceptor(): Interceptor {
        return ItunesHostInterceptor(urlProvider)
    }

    override fun provideBrewHostInterceptor(): Interceptor {
        return BrewHostInterceptor(urlProvider)
    }
}
