package com.example.aheena.di.modules

import com.example.core.network.InterceptorProvider
import com.example.core.network.OkHttpClientBuilderFactory
import com.example.core.network.RetrofitBuilderFactory
import com.example.core_impl.network.InterceptorProviderImpl
import com.example.core_impl.network.OkHttpClientBuilderFactoryImpl
import com.example.core_impl.network.RetrofitBuilderFactoryImpl
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @Binds
    fun bindRetrofitBuilderFactory(factory: RetrofitBuilderFactoryImpl): RetrofitBuilderFactory

    @Binds
    fun bindOkHttpClientBuilderFactory(factory: OkHttpClientBuilderFactoryImpl): OkHttpClientBuilderFactory

    @Binds
    fun bindInterceptorProvider(provider: InterceptorProviderImpl): InterceptorProvider
}
