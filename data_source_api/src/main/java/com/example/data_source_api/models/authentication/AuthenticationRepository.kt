package com.example.data_source_api.models.authentication

interface AuthenticationRepository {

    fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence)
    fun clearRefreshToken()
}
