package com.example.core_impl.controller.logout

import com.example.core_api.controller.logout.LogoutController
import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.router.NavRouter
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import javax.inject.Inject

class LogoutControllerImpl @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    private val sessionController: UserSessionController,
    @MainRouter private val mainRouter: NavRouter,
) : LogoutController {

    override fun logout() {
        authenticationInteractor.clearRefreshToken()
        authenticationInteractor.clearPin()
        sessionController.logoutSession()
    }
}
