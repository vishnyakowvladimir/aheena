package com.example.core_impl.network

import com.example.core.network.InterceptorProvider
import com.example.core.network.OkHttpClientBuilderFactory
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpClientBuilderFactoryImpl @Inject constructor(
    private val interceptorProvider: InterceptorProvider,
) : OkHttpClientBuilderFactory {

    override fun createBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(interceptorProvider.provideHttpLoggingInterceptor())
    }
}
