package com.example.core_impl.controller.logout

import com.example.core_api.controller.logout.LogoutController
import com.example.core_api.controller.session.UserSessionController
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import javax.inject.Inject

class LogoutControllerImpl @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    private val sessionController: UserSessionController,
) : LogoutController {

    override fun logout() {
        authenticationInteractor.clearRefreshToken()
        authenticationInteractor.clearPin()
        sessionController.logoutSession()
    }
}
