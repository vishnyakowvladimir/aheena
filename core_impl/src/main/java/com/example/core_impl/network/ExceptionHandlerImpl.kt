package com.example.core_impl.network

import com.example.core_api.controller.logout.LogoutController
import com.example.core_api.network.ExceptionHandler
import com.example.core_api.network.model.BaseApiException
import com.example.core_api.utils.eventbus.AppEventBus
import com.example.core_api.utils.eventbus.model.AppEvent
import javax.inject.Inject

class ExceptionHandlerImpl @Inject constructor(
    private val eventBus: AppEventBus,
    private val logoutController: LogoutController,
) : ExceptionHandler {

    override fun onException(exception: Exception) {
        when (exception) {
            is BaseApiException.ConnectivityException -> onConnectivityException()
            is BaseApiException.AuthenticationException -> onAuthenticationException()
            else -> Unit
        }
    }

    private fun onConnectivityException() {
        eventBus.sendEvent(AppEvent.OnNoInternetConnection)
    }

    private fun onAuthenticationException() {
        logoutController.logout()
    }
}
