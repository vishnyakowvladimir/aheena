package com.example.data_source_api.source.authentication

interface AuthenticationSource {

    fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence)
    fun clearRefreshToken()
}
