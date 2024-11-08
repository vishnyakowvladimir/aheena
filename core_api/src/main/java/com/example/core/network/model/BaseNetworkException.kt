package com.example.core.network.model

import androidx.annotation.StringRes
import com.example.core_api.R

sealed class BaseNetworkException(
    @StringRes open val defaultMessageId: Int,
    message: String? = null,
    throwable: Throwable? = null,
) : Exception(message, throwable) {

    data class ConnectivityException(
        override val message: String? = null,
        val throwable: Throwable? = null,
    ) : BaseNetworkException(
        defaultMessageId = R.string.connectivity_error,
        message = message,
        throwable = throwable,
    )

    data class NetworkException(
        override val message: String? = null,
        val throwable: Throwable? = null,
    ) : BaseNetworkException(
        defaultMessageId = R.string.default_network_error,
        message = message,
        throwable = throwable,
    )
}
