package com.example.data_source_impl.repository.authentication

import com.example.core.crypto.rsa.cipher.model.AuthenticationCryptoObject
import com.example.data_source_api.repository.authentication.AuthenticationRepository
import com.example.data_source_api.storage.authentication.AuthenticationStorage
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationSource: AuthenticationStorage
) : AuthenticationRepository {

    override fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence) {
        authenticationSource.saveRefreshToken(refreshToken, pinCode)
    }

    override fun getRefreshToken(pinCode: CharSequence): CharSequence {
        return authenticationSource.getRefreshToken(pinCode)
    }

    override fun isRefreshTokenExist(): Boolean {
        return authenticationSource.isRefreshTokenExist()
    }

    override fun clearRefreshToken() {
        authenticationSource.clearRefreshToken()
    }

    override fun savePin(pinCode: CharSequence): Boolean {
        return authenticationSource.savePin(pinCode)
    }

    override fun getPin(authenticatedCryptoObject: AuthenticationCryptoObject): CharSequence {
        return authenticationSource.getPin(authenticatedCryptoObject)
    }

    override fun clearPin() {
        authenticationSource.clearPin()
    }

    override fun getAuthenticationCryptoObject(): AuthenticationCryptoObject {
        return authenticationSource.getAuthenticationCryptoObject()
    }
}
