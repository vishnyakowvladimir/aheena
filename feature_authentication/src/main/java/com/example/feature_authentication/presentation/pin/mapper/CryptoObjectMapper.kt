package com.example.feature_authentication.presentation.pin.mapper

import androidx.biometric.BiometricPrompt
import com.example.core.crypto.rsa.cipher.model.AuthenticationCryptoObject
import javax.inject.Inject

class CryptoObjectMapper @Inject constructor() {

    fun map(cryptoObject: AuthenticationCryptoObject): BiometricPrompt.CryptoObject {
        return BiometricPrompt.CryptoObject(cryptoObject.cipher)
    }

    fun map(biometricPromptCryptoObject: BiometricPrompt.CryptoObject): AuthenticationCryptoObject {
        return AuthenticationCryptoObject(
            requireNotNull(biometricPromptCryptoObject.cipher),
        )
    }
}
