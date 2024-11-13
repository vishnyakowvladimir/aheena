package com.example.data_sdk_api.interactor.authentication

import com.example.core_api.crypto.rsa.cipher.model.CipherHolder

interface AuthenticationInteractor {

    fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence)
    fun getRefreshToken(pinCode: CharSequence): CharSequence
    fun isRefreshTokenExist(): Boolean
    fun clearRefreshToken()
    fun savePin(pinCode: CharSequence): Boolean
    fun getPin(cipher: CipherHolder): CharSequence
    fun clearPin()
    fun getCipher(): CipherHolder
}
