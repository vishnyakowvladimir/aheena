package com.example.feature_authentication.presentation.biometric.mvi.handler

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
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
) :
    SideEffectHandler<BiometricsEvent, BiometricsSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<BiometricsSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<BiometricsEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: BiometricsSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<BiometricsEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is BiometricsSideEffect.Domain.SavePinCode -> handleSavePinCode()
                is BiometricsSideEffect.Domain.SaveOnBiometricFlag -> handleSavePinCode()
                is BiometricsSideEffect.Domain.SaveOffBiometricFlag -> handleSavePinCode()
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

    private fun handleSaveOnBiometricFlag(): Flow<BiometricsEvent> {
        return flow {

        }
    }

    private fun handleSaveOffBiometricFlag(): Flow<BiometricsEvent> {
        return localAuthenticationInteractor.getPinCode().flatMapMerge { pinCode ->
            flow {
                authenticationInteractor.savePin(pinCode)
                emit(BiometricsEvent.Domain.OnPinCodeSaved)
            }
        }
    }
}

