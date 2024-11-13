package com.example.aheena.di.modules

import com.example.core_api.network.InterceptorProvider
import com.example.core_api.network.OkHttpClientBuilderFactory
import com.example.core_api.network.RetrofitBuilderFactory
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
            .build()

        val retrofit = retrofitBuilderFactory
            .createBuilder()
            .client(okHttpClient)
            .build()

        return retrofit.create(ItunesApi::class.java)
    }
}
