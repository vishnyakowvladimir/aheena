package com.example.core_api.network

import okhttp3.Interceptor

interface InterceptorProvider {

    fun provideHttpLoggingInterceptor(): Interceptor

    fun provideChuckerInterceptor(): Interceptor

    fun provideItunesHostInterceptor(): Interceptor

    fun provideBrewHostInterceptor(): Interceptor

    fun provideConnectivityInterceptor(): Interceptor

    fun provideExceptionInterceptor(): Interceptor
}
