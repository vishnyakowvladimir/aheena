package com.example.aheena.di.modules

import com.example.aheena.BuildConfig
import com.example.core_api.application_info.ApplicationInfo
import com.example.core_api.network.ExceptionHandler
import com.example.core_api.network.InterceptorProvider
import com.example.core_api.network.OkHttpClientBuilderFactory
import com.example.core_api.network.RetrofitBuilderFactory
import com.example.core_api.utils.shared_preferences.AndroidPreferencesProvider
import com.example.core_impl.network.ExceptionHandlerImpl
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

    @Binds
    fun bindExceptionHandler(handler: ExceptionHandlerImpl): ExceptionHandler

    companion object {

        @Provides
        fun provideUrlProvider(
            preferencesProvider: AndroidPreferencesProvider,
            applicationInfo: ApplicationInfo,
        ): UrlProvider {
            return UrlProviderImpl(
                incorrectUrls = BaseUrls(
                    itunesUrl = BuildConfig.INCORRECT_URL_ITUNES,
                    brewUrl = BuildConfig.INCORRECT_URL_BREW,
                ),
                prodUrls = BaseUrls(
                    itunesUrl = BuildConfig.PROD_URL_ITUNES,
                    brewUrl = BuildConfig.PROD_URL_BREW,
                ),
                testUrls = BaseUrls(
                    itunesUrl = BuildConfig.TEST_URL_ITUNES,
                    brewUrl = BuildConfig.TEST_URL_BREW,
                ),
                preferencesProvider = preferencesProvider,
                applicationInfo = applicationInfo,
            )
        }
    }
}
