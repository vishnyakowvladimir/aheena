package com.example.feature_authentication.presentation.create_pin.mvi.handler

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.feature_authentication.domain.LocalAuthenticationInteractor
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
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
internal class CreatePinDomainSideEffectHandler @Inject constructor(
    private val localAuthenticationInteractor: LocalAuthenticationInteractor,
    private val authenticationInteractor: AuthenticationInteractor,
) :
    SideEffectHandler<CreatePinEvent, CreatePinSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<CreatePinSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<CreatePinEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: CreatePinSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<CreatePinEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is CreatePinSideEffect.Domain.SaveRefreshToken -> handleSaveRefreshToken(sideEffect)
            }
        }
            .flowOn(Dispatchers.IO)
    }

    private fun handleSaveRefreshToken(sideEffect: CreatePinSideEffect.Domain.SaveRefreshToken): Flow<CreatePinEvent> {
        return localAuthenticationInteractor.getRefreshToken()
            .flatMapMerge { refreshToken ->
                flow {
                    delay(1000)
                    val pinCode = convertIntegersToCharSequence(sideEffect.pinCode)

                    // сохраняем refreshToken на основе пин-кода
                    authenticationInteractor.saveRefreshToken(
                        refreshToken = refreshToken,
                        pinCode = pinCode,
                    )

                    // временно сохраняем пин-код
                    localAuthenticationInteractor.savePinCode(pinCode)

                    emit(CreatePinEvent.Domain.OnRefreshTokenSaved)
                }
            }
    }

    private fun convertIntegersToCharSequence(list: List<Int>): CharSequence {
        return list.fold("") { currentString, value ->
            currentString.plus(value)
        }
    }
}

