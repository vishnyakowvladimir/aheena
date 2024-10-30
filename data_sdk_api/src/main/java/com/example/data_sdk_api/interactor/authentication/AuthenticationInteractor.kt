package com.example.data_sdk_api.interactor.authentication

import com.example.core.crypto.rsa.model.AuthenticationCryptoObject

interface AuthenticationInteractor {

    fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence)
    fun getRefreshToken(pinCode: CharSequence): CharSequence
    fun isRefreshTokenExist(): Boolean
    fun clearRefreshToken()
    fun savePin(pinCode: CharSequence): Boolean
    fun getPin(authenticatedCryptoObject: AuthenticationCryptoObject): CharSequence
    fun clearPin()
}
