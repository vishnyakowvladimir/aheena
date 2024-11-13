package com.example.feature_authentication.presentation.pin.mapper

import androidx.biometric.BiometricPrompt
import com.example.core_api.crypto.rsa.cipher.model.CipherHolder
import javax.inject.Inject

class CryptoObjectMapper @Inject constructor() {

    fun map(cryptoObject: CipherHolder): BiometricPrompt.CryptoObject {
        return BiometricPrompt.CryptoObject(cryptoObject.cipher)
    }

    fun map(biometricPromptCryptoObject: BiometricPrompt.CryptoObject): CipherHolder {
        return CipherHolder(
            requireNotNull(biometricPromptCryptoObject.cipher),
        )
    }
}
