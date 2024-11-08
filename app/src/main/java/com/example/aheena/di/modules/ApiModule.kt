package com.example.aheena.di.modules

import com.example.core.network.InterceptorProvider
import com.example.core.network.OkHttpClientBuilderFactory
import com.example.core.network.RetrofitBuilderFactory
import com.example.data_source_impl.api.ItunesApi
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideItunesApi(
        retrofitBuilderFactory: RetrofitBuilderFactory,
        okHttpClientBuilderFactory: OkHttpClientBuilderFactory,
        interceptorProvider: InterceptorProvider,
    ): ItunesApi {
        val okHttpClient = okHttpClientBuilderFactory
            .createBuilder()
            .addInterceptor(interceptorProvider.provideItunesHostInterceptor())
            .addInterceptor(interceptorProvider.provideConnectivityInterceptor())
            .addInterceptor(interceptorProvider.provideExceptionInterceptor())
            .build()

        return retrofitBuilderFactory
            .createBuilder()
            .client(okHttpClient)
            .build()
            .create(ItunesApi::class.java)
    }
}
