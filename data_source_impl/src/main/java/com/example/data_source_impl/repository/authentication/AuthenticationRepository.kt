package com.example.data_source_impl.repository.authentication

import com.example.data_source_api.models.authentication.AuthenticationRepository
import com.example.data_source_api.source.authentication.AuthenticationSource
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationSource: AuthenticationSource
) : AuthenticationRepository {

    override fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence) {
        authenticationSource.saveRefreshToken(refreshToken, pinCode)
    }

    override fun clearRefreshToken() {
        authenticationSource.clearRefreshToken()
    }
}
