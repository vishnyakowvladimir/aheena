package com.example.feature_authentication.data

import javax.inject.Inject

internal class LocalAuthenticationSource @Inject constructor() {

    private var refreshToken: CharSequence? = null

    fun saveRefreshToken(refreshToken: CharSequence) {
        this.refreshToken = refreshToken
    }

    fun getRefreshToken(): CharSequence? {
        return refreshToken
    }
}
