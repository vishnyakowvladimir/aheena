package com.example.feature_authentication.biometric.model

import androidx.biometric.BiometricManager.Authenticators as BiometricManagerAuthenticators

enum class BiometricAuthenticators(val value: Int) {
    STRONG(BiometricManagerAuthenticators.BIOMETRIC_STRONG),
    WEAK(BiometricManagerAuthenticators.BIOMETRIC_WEAK),
    DEVICE_CREDENTIAL(BiometricManagerAuthenticators.DEVICE_CREDENTIAL),
}
