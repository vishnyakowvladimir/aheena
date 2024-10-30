package com.example.feature_authentication.domain

import com.example.feature_authentication.data.LocalAuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class LocalAuthenticationInteractor @Inject constructor(
    private val repository: LocalAuthenticationRepository
) {

    fun saveRefreshToken(refreshToken: CharSequence) {
        repository.saveRefreshToken(refreshToken)
    }

    fun getRefreshToken(): Flow<CharSequence> {
        return repository.getRefreshToken()
    }

    fun savePinCode(pinCode: CharSequence) {
        repository.savePinCode(pinCode)
    }

    fun getPinCode(): Flow<CharSequence> {
        return repository.getPinCode()
    }
}
