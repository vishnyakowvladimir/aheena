package com.example.data_source_impl.source.authentication

import android.util.Base64
import androidx.core.content.edit
import com.example.core.crypto.authentication.RefreshTokenCipher
import com.example.core.crypto.rsa.AuthenticationRsaCipher
import com.example.core.crypto.rsa.model.AuthenticationCryptoObject
import com.example.core.utils.shared_preferences.AndroidPreferencesProvider
import com.example.data_source_api.source.authentication.AuthenticationSource
import javax.inject.Inject

private const val REFRESH_TOKEN_KEY = "refresh_token"
private const val PIN_CODE_KEY = "pin_code"

class AuthenticationSourceImpl @Inject constructor(
    private val preferencesProvider: AndroidPreferencesProvider,
    private val refreshTokenCipher: RefreshTokenCipher,
    private val rsaCipher: AuthenticationRsaCipher,
) : AuthenticationSource {
    override fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence) {
        preferencesProvider.cryptoPrefs.edit {
            putString(REFRESH_TOKEN_KEY, refreshTokenCipher.encrypt(refreshToken, pinCode).toString())
        }
    }

    override fun getRefreshToken(pinCode: CharSequence): CharSequence {
        val encryptedRefreshToken =
            preferencesProvider.cryptoPrefs.getString(REFRESH_TOKEN_KEY, "") as CharSequence

        return refreshTokenCipher.decrypt(encryptedRefreshToken, pinCode)
    }

    override fun isRefreshTokenExist(): Boolean {
        return preferencesProvider.cryptoPrefs.getString(REFRESH_TOKEN_KEY, "").orEmpty().isNotEmpty()
    }

    override fun clearRefreshToken() {
        preferencesProvider.cryptoPrefs.edit {
            remove(REFRESH_TOKEN_KEY)
        }
    }

    override fun savePin(pinCode: CharSequence): Boolean {
        return try {
            preferencesProvider.cryptoPrefs.edit {
                val pinCodeBytes = pinCode.toString().toByteArray(Charsets.UTF_8)
                val encryptedPinCode = rsaCipher.encrypt(pinCodeBytes)
                val base64PinCode = Base64.encodeToString(encryptedPinCode, Base64.NO_WRAP)
                putString(PIN_CODE_KEY, base64PinCode)
            }
            true
        } catch (e: Throwable) {
            false
        }
    }

    override fun getPin(authenticatedCryptoObject: AuthenticationCryptoObject): CharSequence {
        return try {
            val base64PinCode = preferencesProvider.cryptoPrefs.getString(PIN_CODE_KEY, null)
                ?: return ""
            val encryptedPinCode = Base64.decode(base64PinCode, Base64.NO_WRAP)
            val decryptedPinCode = rsaCipher.decrypt(encryptedPinCode, authenticatedCryptoObject)
            String(decryptedPinCode, Charsets.UTF_8)
        } catch (throwable: Throwable) {
            ""
        }
    }

    override fun clearPin() {
        preferencesProvider.cryptoPrefs.edit {
            remove(PIN_CODE_KEY)
        }
    }

    override fun getAuthenticationCryptoObject(): AuthenticationCryptoObject {
        return rsaCipher.getAuthenticationCryptoObject()
    }
}
