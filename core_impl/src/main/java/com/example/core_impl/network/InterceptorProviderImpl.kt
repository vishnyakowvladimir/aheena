package com.example.core_impl.network

import android.support.multidex.BuildConfig
import com.example.core.network.InterceptorProvider
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class InterceptorProviderImpl @Inject constructor() : InterceptorProvider {

    override fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
            }
    }
}
