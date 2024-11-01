package com.example.core_impl.controller.logout

import com.example.core.controller.logout.LogoutController
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.core.navigation.router.AppRouter
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import javax.inject.Inject

class LogoutControllerImpl @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    private val appRouter: AppRouter,
) : LogoutController {

    override fun logout() {
        authenticationInteractor.clearRefreshToken()
        authenticationInteractor.clearPin()
        appRouter.replaceAll(FeaturesDestination.AuthenticationDestination)
    }
}
