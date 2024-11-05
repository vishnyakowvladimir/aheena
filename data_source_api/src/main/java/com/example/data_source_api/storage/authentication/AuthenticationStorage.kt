package com.example.data_source_api.storage.authentication

import com.example.core.crypto.rsa.cipher.model.CipherHolder

interface AuthenticationStorage {

    fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence)
    fun getRefreshToken(pinCode: CharSequence): CharSequence
    fun isRefreshTokenExist(): Boolean
    fun clearRefreshToken()
    fun savePin(pinCode: CharSequence): Boolean
    fun getPin(cipher: CipherHolder): CharSequence
    fun clearPin()
    fun getCipher(): CipherHolder
}
