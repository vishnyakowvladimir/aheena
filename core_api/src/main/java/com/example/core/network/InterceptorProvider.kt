package com.example.core.network

import okhttp3.Interceptor

interface InterceptorProvider {

    fun provideHttpLoggingInterceptor(): Interceptor
}
