package com.example.data_sdk_impl.interactor.authentication

import com.example.core.crypto.rsa.model.AuthenticationCryptoObject
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.data_source_api.repository.authentication.AuthenticationRepository
import javax.inject.Inject

class AuthenticationInteractorImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : AuthenticationInteractor {

    override fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence) {
        authenticationRepository.saveRefreshToken(refreshToken, pinCode)
    }

    override fun getRefreshToken(pinCode: CharSequence): CharSequence {
        return authenticationRepository.getRefreshToken(pinCode)
    }

    override fun isRefreshTokenExist(): Boolean {
        return authenticationRepository.isRefreshTokenExist()
    }

    override fun clearRefreshToken() {
        authenticationRepository.clearRefreshToken()
    }

    override fun savePin(pinCode: CharSequence): Boolean {
        return authenticationRepository.savePin(pinCode)
    }

    override fun getPin(authenticatedCryptoObject: AuthenticationCryptoObject): CharSequence {
        return authenticationRepository.getPin(authenticatedCryptoObject)
    }

    override fun clearPin() {
        authenticationRepository.clearPin()
    }
}
