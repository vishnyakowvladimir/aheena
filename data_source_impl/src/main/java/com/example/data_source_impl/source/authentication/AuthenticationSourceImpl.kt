package com.example.data_source_impl.source.authentication

import androidx.core.content.edit
import com.example.core.crypto.authentication.RefreshTokenCipher
import com.example.core.utils.shared_preferences.AndroidPreferencesProvider
import com.example.data_source_api.source.authentication.AuthenticationSource
import javax.inject.Inject

private const val REFRESH_TOKEN_KEY = "refresh_token"

class AuthenticationSourceImpl @Inject constructor(
    private val preferencesProvider: AndroidPreferencesProvider,
    private val refreshTokenCipher: RefreshTokenCipher,
) : AuthenticationSource {
    override fun saveRefreshToken(refreshToken: String, pinCode: String) {
        preferencesProvider.cryptoPrefs.edit {
            putString(REFRESH_TOKEN_KEY, refreshTokenCipher.encrypt(refreshToken, pinCode))
        }
    }

    override fun clearRefreshToken() {
        preferencesProvider.cryptoPrefs.edit {
            remove(REFRESH_TOKEN_KEY)
        }
    }
}
