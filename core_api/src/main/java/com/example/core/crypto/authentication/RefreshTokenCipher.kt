package com.example.core.crypto.authentication

interface RefreshTokenCipher {

    fun encrypt(refreshToken: CharSequence, pinCode: CharSequence): CharSequence
    fun decrypt(refreshToken: CharSequence, pinCode: CharSequence): CharSequence
}
