package com.example.feature_authentication.biometric.model

import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS

enum class BiometricAuthenticationPossibility(val id: Int) {
    ERROR_HW_UNAVAILABLE(BIOMETRIC_ERROR_HW_UNAVAILABLE),
    ERROR_NONE_ENROLLED(BIOMETRIC_ERROR_NONE_ENROLLED),
    ERROR_NO_HARDWARE(BIOMETRIC_ERROR_NO_HARDWARE),
    ERROR_SECURITY_UPDATE_REQUIRED(BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED),
    SUCCESS(BIOMETRIC_SUCCESS),
    UNDEFINED(Int.MIN_VALUE);

    val isSuccess: Boolean get() = this == SUCCESS
    val isNoneEnrolled: Boolean get() = this == ERROR_NONE_ENROLLED

    companion object {
        fun getById(id: Int): BiometricAuthenticationPossibility {
            return entries.find { it.id == id } ?: UNDEFINED
        }
    }
}
