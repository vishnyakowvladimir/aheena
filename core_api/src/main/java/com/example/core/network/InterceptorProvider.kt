package com.example.core.network

import okhttp3.Interceptor

interface InterceptorProvider {

    fun provideHttpLoggingInterceptor(): Interceptor

    fun provideItunesHostInterceptor(): Interceptor

    fun provideBrewHostInterceptor(): Interceptor

    fun provideConnectivityInterceptor(): Interceptor

    fun provideExceptionInterceptor(): Interceptor
}
