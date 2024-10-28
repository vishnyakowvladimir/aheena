package com.example.feature_authentication.domain

import com.example.feature_authentication.data.LocalAuthenticationRepository
import javax.inject.Inject

internal class LocalAuthenticationInteractor @Inject constructor(
    private val repository: LocalAuthenticationRepository
) {

    fun saveRefreshToken(refreshToken: CharSequence) {
        repository.saveRefreshToken(refreshToken)
    }

    fun getRefreshToken(): CharSequence? {
        return repository.getRefreshToken()
    }
}
