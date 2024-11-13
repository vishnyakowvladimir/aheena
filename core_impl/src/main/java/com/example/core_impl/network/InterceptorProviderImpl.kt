package com.example.core_impl.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.core.application_info.ApplicationInfo
import com.example.core.network.InterceptorProvider
import com.example.core.utils.connectivity_checker.ConnectivityChecker
import com.example.core.utils.eventbus.AppEventBus
import com.example.core_impl.network.interceptor.BrewHostInterceptor
import com.example.core_impl.network.interceptor.ConnectivityInterceptor
import com.example.core_impl.network.interceptor.ExceptionInterceptor
import com.example.core_impl.network.interceptor.ItunesHostInterceptor
import com.example.data_source_api.storage.network.UrlProvider
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class InterceptorProviderImpl @Inject constructor(
    private val urlProvider: UrlProvider,
    private val connectivityChecker: ConnectivityChecker,
    private val eventBus: AppEventBus,
    private val context: Context,
    private val applicationInfo: ApplicationInfo,
) : InterceptorProvider {

    override fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .apply {
                level =
                    if (applicationInfo.isDebug) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.BASIC
                    }
            }
    }

    override fun provideChuckerInterceptor(): Interceptor {
        return ChuckerInterceptor(context)
    }

    override fun provideItunesHostInterceptor(): Interceptor {
        return ItunesHostInterceptor(urlProvider)
    }

    override fun provideBrewHostInterceptor(): Interceptor {
        return BrewHostInterceptor(urlProvider)
    }

    override fun provideConnectivityInterceptor(): Interceptor {
        return ConnectivityInterceptor(
            connectivityChecker = connectivityChecker,
            eventBus = eventBus,
        )
    }

    override fun provideExceptionInterceptor(): Interceptor {
        return ExceptionInterceptor()
    }
}
