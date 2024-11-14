package com.example.core_impl.network.interceptor

import com.example.core_api.network.ExceptionHandler
import com.example.core_api.network.model.BaseApiException
import okhttp3.Interceptor
import okhttp3.Response

class ExceptionInterceptor(
    private val exceptionHandler: ExceptionHandler,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        return try {
            val request = chain.request()
            val response = chain.proceed(request)
            throwExceptionWhenResponseIsNotSuccessful(response)
            response
        } catch (exception: Exception) {
            val defaultException = BaseApiException.DefaultApiException(message = exception.message)
            exceptionHandler.onException(defaultException)
            throw defaultException
        }
    }

    private fun throwExceptionWhenResponseIsNotSuccessful(response: Response) {
        if (!response.isSuccessful) {
            val defaultException = BaseApiException.DefaultApiException(
                message = response.message,
            )
            exceptionHandler.onException(defaultException)
            throw defaultException
        }
    }
}
