package com.example.feature_authentication.presentation.container

import com.example.core_api.presentation.base.BaseViewModel
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import javax.inject.Inject

class AuthenticationContainerViewModel @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
) : BaseViewModel() {

    fun isRefreshTokenExist(): Boolean {
        return authenticationInteractor.isRefreshTokenExist()
    }
}
