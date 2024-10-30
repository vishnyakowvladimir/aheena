package com.example.feature_authentication.presentation.biometric.mvi.handler

import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class BiometricsSideEffectHandler @Inject constructor(
    private val uiHandler: BiometricsUiSideEffectHandler,
    private val domainHandler: BiometricsDomainSideEffectHandler,
) : SideEffectHandler<BiometricsEvent, BiometricsSideEffect> {

    override fun getEventSource(): Flow<BiometricsEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: BiometricsSideEffect) {
        when (sideEffect) {
            is BiometricsSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is BiometricsSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
        }
    }
}
