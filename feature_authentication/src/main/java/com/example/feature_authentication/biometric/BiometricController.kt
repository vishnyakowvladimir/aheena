package com.example.feature_authentication.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.core.holder.ActivityHolder
import com.example.data_sdk_api.interactor.biometrics.BiometricsInteractor
import com.example.feature_authentication.biometric.model.AuthenticationErrorType
import com.example.feature_authentication.biometric.model.BiometricAuthenticationPossibility
import com.example.feature_authentication.biometric.model.BiometricAuthenticationResult
import com.example.feature_authentication.biometric.model.BiometricAuthenticators
import javax.inject.Inject

internal class BiometricController @Inject constructor(
    private val applicationContext: Context,
    val activityHolder: ActivityHolder,
    private val biometricsInteractor: BiometricsInteractor,
) {

    fun isHardwareUnavailable(): Boolean {
        val authenticationPossibility = canAuthenticate(BiometricAuthenticators.STRONG)
        return authenticationPossibility == BiometricAuthenticationPossibility.ERROR_NO_HARDWARE ||
            authenticationPossibility == BiometricAuthenticationPossibility.ERROR_HW_UNAVAILABLE ||
            authenticationPossibility == BiometricAuthenticationPossibility.ERROR_SECURITY_UPDATE_REQUIRED
    }

    fun canAuthenticate(
        authenticator: BiometricAuthenticators,
        vararg authenticators: BiometricAuthenticators,
    ): BiometricAuthenticationPossibility {
        val authenticatorsValue = authenticators
            .map { it.value }
            .fold(authenticator.value) { current, next -> current or next }

        return BiometricAuthenticationPossibility
            .getById(BiometricManager.from(applicationContext).canAuthenticate(authenticatorsValue))
    }

    fun saveEnabledBiometricsFlag(flag: Boolean) {
        biometricsInteractor.saveEnabledBiometricsFlag(flag)
    }

    fun isReady(): Boolean {
        return canAuthenticate(BiometricAuthenticators.STRONG).isSuccess && isBiometricsEnabled()
    }

    fun showBiometricsWindow(
        promptInfo: BiometricPrompt.PromptInfo,
        cryptoObject: BiometricPrompt.CryptoObject,
        resultAction: (result: BiometricAuthenticationResult) -> Unit,
    ): BiometricPromptHandler {
        val activity = requireNotNull(activityHolder.activity)

        val prompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            createAuthenticationCallback(resultAction)
        )
        prompt.authenticate(promptInfo, cryptoObject)
        return BiometricPromptHandler { prompt.cancelAuthentication() }
    }

    private fun createAuthenticationCallback(
        resultAction: (result: BiometricAuthenticationResult) -> Unit,
    ): BiometricPrompt.AuthenticationCallback {
        return object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                resultAction(BiometricAuthenticationResult.Success(result))
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                resultAction(
                    BiometricAuthenticationResult.Error(AuthenticationErrorType.getByErrorCode(errorCode), errString)
                )
            }

            override fun onAuthenticationFailed() {
                resultAction(BiometricAuthenticationResult.Failure)
            }
        }
    }

    private fun isBiometricsEnabled(): Boolean {
        return biometricsInteractor.isBiometricsEnabled()
    }
}

internal fun interface BiometricPromptHandler {
    fun dismiss()
}
