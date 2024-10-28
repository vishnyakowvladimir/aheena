package com.example.feature_authentication.data

import javax.inject.Inject

internal class LocalAuthenticationRepository @Inject constructor(
    private val source: LocalAuthenticationSource
) {

    fun saveRefreshToken(refreshToken: CharSequence) {
        source.saveRefreshToken(refreshToken)
    }

    fun getRefreshToken(): CharSequence? {
        return source.getRefreshToken()
    }
}
