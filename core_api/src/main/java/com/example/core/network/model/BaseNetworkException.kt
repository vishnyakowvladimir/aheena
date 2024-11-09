package com.example.core.network.model

import java.io.IOException

sealed class BaseNetworkException(message: String?) : IOException(message) {

    class ConnectivityException(message: String? = null) : BaseNetworkException(message)
    class NetworkException(message: String? = null) : BaseNetworkException(message)
}
