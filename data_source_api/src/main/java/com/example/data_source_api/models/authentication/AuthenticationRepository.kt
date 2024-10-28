package com.example.data_source_api.models.authentication

interface AuthenticationRepository {

    fun saveRefreshToken(refreshToken: String, pinCode: String)
    fun clearRefreshToken()
}
