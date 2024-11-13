package com.example.feature_authentication.presentation.container

import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.presentation.base.BaseViewModel
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import javax.inject.Inject

class AuthenticationContainerViewModel @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    userSessionController: UserSessionController,
) : BaseViewModel() {

    init {
        userSessionController.disable()
    }

    fun isRefreshTokenExist(): Boolean {
        return authenticationInteractor.isRefreshTokenExist()
    }
}
