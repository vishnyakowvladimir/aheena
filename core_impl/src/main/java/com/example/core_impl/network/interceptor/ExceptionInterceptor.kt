package com.example.core_impl.network.interceptor

import com.example.core_api.network.model.BaseApiException
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
            throw BaseApiException.DefaultApiException(message = exception.message)
        }
    }

    private fun throwExceptionWhenResponseIsNotSuccessful(response: Response) {
        if (!response.isSuccessful) {
            throw BaseApiException.DefaultApiException(
                message = response.message,
            )
        }
    }
}
