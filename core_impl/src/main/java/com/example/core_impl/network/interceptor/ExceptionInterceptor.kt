package com.example.core_impl.network.interceptor

import com.example.core.network.model.BaseNetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ExceptionInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        return try {
            val request = chain.request()
            val response = chain.proceed(request)
            throwExceptionWhenResponseIsNotSuccessful(response)
            response
        } catch (exception: Exception) {
            throw BaseNetworkException.NetworkException(message = exception.message)
        }
    }

    private fun throwExceptionWhenResponseIsNotSuccessful(response: Response) {
        if (!response.isSuccessful) {
            throw BaseNetworkException.NetworkException(
                message = response.message,
            )
        }
    }
}
