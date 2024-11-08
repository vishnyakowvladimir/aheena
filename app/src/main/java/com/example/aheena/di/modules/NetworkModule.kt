package com.example.aheena.di.modules

import com.example.core.network.InterceptorProvider
import com.example.core.network.OkHttpClientBuilderFactory
import com.example.core.network.RetrofitBuilderFactory
import com.example.core.utils.shared_preferences.AndroidPreferencesProvider
import com.example.core_impl.network.InterceptorProviderImpl
import com.example.core_impl.network.OkHttpClientBuilderFactoryImpl
import com.example.core_impl.network.RetrofitBuilderFactoryImpl
import com.example.data_source_api.models.network.BaseUrls
import com.example.data_source_api.storage.network.UrlProvider
import com.example.data_source_impl.storage.network.UrlProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface NetworkModule {

    @Binds
    fun bindRetrofitBuilderFactory(factory: RetrofitBuilderFactoryImpl): RetrofitBuilderFactory

    @Binds
    fun bindOkHttpClientBuilderFactory(factory: OkHttpClientBuilderFactoryImpl): OkHttpClientBuilderFactory

    @Binds
    fun bindInterceptorProvider(provider: InterceptorProviderImpl): InterceptorProvider

    companion object {

        @Provides
        fun provideUrlProvider(preferencesProvider: AndroidPreferencesProvider): UrlProvider {
            return UrlProviderImpl(
                incorrectUrls = BaseUrls(
                    itunesUrl = "",
                    brewUrl = "",
                ),
                prodUrls = BaseUrls(
                    itunesUrl = "",
                    brewUrl = "",
                ),
                testUrls = BaseUrls(
                    itunesUrl = "",
                    brewUrl = "",
                ),
                preferencesProvider = preferencesProvider,
            )
        }
    }
}
