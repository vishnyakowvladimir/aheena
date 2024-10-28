package com.example.data_source_api.source.authentication

interface AuthenticationSource {

    fun saveRefreshToken(refreshToken: String, pinCode: String)
    fun clearRefreshToken()
}
