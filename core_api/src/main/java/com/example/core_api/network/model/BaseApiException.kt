package com.example.core_api.network.model

import java.io.IOException

sealed class BaseApiException(message: String?, throwable: Throwable?) : IOException(message, throwable) {

    class ConnectivityException(message: String? = null) : BaseApiException(message, null)
    class DefaultApiException(
        message: String? = null,
        throwable: Throwable? = null,
    ) : BaseApiException(message, throwable)
}
