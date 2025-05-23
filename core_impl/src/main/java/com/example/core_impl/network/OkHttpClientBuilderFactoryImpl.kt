package com.example.core_impl.network

import com.example.core_api.network.InterceptorProvider
import com.example.core_api.network.OkHttpClientBuilderFactory
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpClientBuilderFactoryImpl @Inject constructor(
    private val interceptorProvider: InterceptorProvider,
) : OkHttpClientBuilderFactory {

    override fun createBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(interceptorProvider.provideChuckerInterceptor())
            .addInterceptor(interceptorProvider.provideHttpLoggingInterceptor())
            .addInterceptor(interceptorProvider.provideConnectivityInterceptor())
            .addInterceptor(interceptorProvider.provideExceptionInterceptor())
    }
}
