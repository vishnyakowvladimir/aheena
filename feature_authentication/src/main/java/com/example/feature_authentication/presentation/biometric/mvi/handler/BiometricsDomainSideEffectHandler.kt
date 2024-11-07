package com.example.feature_authentication.presentation.biometric.mvi.handler

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.feature_authentication.biometric.BiometricController
import com.example.feature_authentication.domain.LocalAuthenticationInteractor
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class BiometricsDomainSideEffectHandler @Inject constructor(
    private val localAuthenticationInteractor: LocalAuthenticationInteractor,
    private val authenticationInteractor: AuthenticationInteractor,
    private val biometricsController: BiometricController,
) :
    SideEffectHandler<BiometricsEvent, BiometricsSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<BiometricsSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<BiometricsEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: BiometricsSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<BiometricsEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is BiometricsSideEffect.Domain.SavePinCode -> handleSavePinCode()
                is BiometricsSideEffect.Domain.SaveBiometricsFlag -> handleSaveBiometricsFlag(sideEffect)
            }
        }
            .flowOn(Dispatchers.IO)
    }

    private fun handleSavePinCode(): Flow<BiometricsEvent> {
        return localAuthenticationInteractor.getPinCode().flatMapMerge { pinCode ->
            flow {
                authenticationInteractor.savePin(pinCode)
                emit(BiometricsEvent.Domain.OnPinCodeSaved)
            }
        }
    }

    private fun handleSaveBiometricsFlag(sideEffect: BiometricsSideEffect.Domain.SaveBiometricsFlag): Flow<BiometricsEvent> {
        return flow {
            biometricsController.saveEnabledBiometricsFlag(sideEffect.flag)
            emit(BiometricsEvent.None)
        }
    }
}

