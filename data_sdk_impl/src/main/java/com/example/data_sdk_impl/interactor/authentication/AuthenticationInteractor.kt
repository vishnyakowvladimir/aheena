package com.example.data_sdk_impl.interactor.authentication

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.data_source_api.models.authentication.AuthenticationRepository
import javax.inject.Inject

class AuthenticationInteractorImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : AuthenticationInteractor {

    override fun saveRefreshToken(refreshToken: String, pinCode: String) {
        authenticationRepository.saveRefreshToken(refreshToken, pinCode)
    }

    override fun clearRefreshToken() {
        authenticationRepository.clearRefreshToken()
    }
}
