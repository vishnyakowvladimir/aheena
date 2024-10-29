package com.example.feature_authentication.biometric.model

import androidx.biometric.BiometricPrompt

sealed interface BiometricAuthenticationResult {
    class Success(val result: BiometricPrompt.AuthenticationResult) : BiometricAuthenticationResult
    class Error(val type: AuthenticationErrorType, val message: CharSequence) : BiometricAuthenticationResult
    data object Failure : BiometricAuthenticationResult
}

enum class AuthenticationErrorType(val code: Int) {
    HW_UNAVAILABLE(BiometricPrompt.ERROR_HW_UNAVAILABLE),
    UNABLE_TO_PROCESS(BiometricPrompt.ERROR_UNABLE_TO_PROCESS),
    TIMEOUT(BiometricPrompt.ERROR_TIMEOUT),
    NO_SPACE(BiometricPrompt.ERROR_NO_SPACE),
    CANCELED(BiometricPrompt.ERROR_CANCELED),
    LOCKOUT(BiometricPrompt.ERROR_LOCKOUT),
    VENDOR(BiometricPrompt.ERROR_VENDOR),
    LOCKOUT_PERMANENT(BiometricPrompt.ERROR_LOCKOUT_PERMANENT),
    USER_CANCELED(BiometricPrompt.ERROR_USER_CANCELED),
    NO_BIOMETRICS(BiometricPrompt.ERROR_NO_BIOMETRICS),
    HW_NOT_PRESENT(BiometricPrompt.ERROR_HW_NOT_PRESENT),
    NEGATIVE_BUTTON(BiometricPrompt.ERROR_NEGATIVE_BUTTON),
    NO_DEVICE_CREDENTIAL(BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL),
    UNKNOWN(-1);

    companion object {
        fun getByErrorCode(errorCode: Int): AuthenticationErrorType {
            return entries.find { it.code == errorCode } ?: UNKNOWN
        }
    }
}

fun AuthenticationErrorType.isLockedType(): Boolean {
    return this == AuthenticationErrorType.LOCKOUT || this == AuthenticationErrorType.LOCKOUT_PERMANENT
}
