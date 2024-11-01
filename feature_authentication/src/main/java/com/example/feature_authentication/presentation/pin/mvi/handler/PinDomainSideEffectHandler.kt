package com.example.feature_authentication.presentation.pin.mvi.handler

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.feature_authentication.constants.REFRESH_TOKEN
import com.example.feature_authentication.domain.LocalAuthenticationInteractor
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class PinDomainSideEffectHandler @Inject constructor(
    private val localAuthenticationInteractor: LocalAuthenticationInteractor,
    private val authenticationInteractor: AuthenticationInteractor,
) :
    SideEffectHandler<PinEvent, PinSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<PinSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<PinEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: PinSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<PinEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is PinSideEffect.Domain.AuthenticationByBiometricNeeded -> {
                    handleAuthenticationByBiometricNeeded(sideEffect)
                }

                is PinSideEffect.Domain.AuthenticationByPinNeeded -> {
                    handleAuthenticationByPinNeeded(sideEffect)
                }
            }
        }
            .flowOn(Dispatchers.IO)
    }

    private fun handleAuthenticationByBiometricNeeded(
        sideEffect: PinSideEffect.Domain.AuthenticationByBiometricNeeded,
    ): Flow<PinEvent> {
        return flow {
            delay(1000)
            val pin = authenticationInteractor.getPin(sideEffect.cryptoObject)
            val refreshToken = authenticationInteractor.getRefreshToken(pin)

            // по идее еще должен отправляться запрос авторизации, где должен прийти успешный или нет ответ
            // но запроса нет
            // поэтому просто проверяю совпал ли refreshToken после дешифрования
            emit(PinEvent.Domain.OnAuthentication(refreshToken == REFRESH_TOKEN))
        }
    }

    private fun handleAuthenticationByPinNeeded(
        sideEffect: PinSideEffect.Domain.AuthenticationByPinNeeded,
    ): Flow<PinEvent> {
        return flow {
            delay(1000)
            val refreshToken = authenticationInteractor.getRefreshToken(sideEffect.pin)

            // по идее еще должен отправляться запрос авторизации, где должен прийти успешный или нет ответ
            // но запроса нет
            // поэтому просто проверяю совпал ли refreshToken после дешифрования
            emit(PinEvent.Domain.OnAuthentication(refreshToken == REFRESH_TOKEN))
        }
    }
}

