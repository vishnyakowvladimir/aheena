package com.example.feature_authentication.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class LocalAuthenticationRepository @Inject constructor(
    private val source: LocalAuthenticationSource
) {

    fun saveRefreshToken(refreshToken: CharSequence) {
        source.saveRefreshToken(refreshToken)
    }

    fun getRefreshToken(): Flow<CharSequence> {
        return source.getRefreshToken()
    }

    fun savePinCode(pinCode: CharSequence) {
        source.savePinCode(pinCode)
    }

    fun getPinCode(): Flow<CharSequence> {
        return source.getPinCode()
    }
}
