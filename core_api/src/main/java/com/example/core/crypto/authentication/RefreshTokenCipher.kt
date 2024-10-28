package com.example.core.crypto.authentication

interface RefreshTokenCipher {

    fun encrypt(refreshToken: String, pinCode: String): String
    fun decrypt(refreshToken: String, pinCode: String): String
}
