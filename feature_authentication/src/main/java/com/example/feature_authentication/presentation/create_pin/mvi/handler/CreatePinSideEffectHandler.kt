package com.example.feature_authentication.presentation.create_pin.mvi.handler

import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class CreatePinSideEffectHandler @Inject constructor(
    private val uiHandler: CreatePinUiSideEffectHandler,
    private val domainHandler: CreatePinDomainSideEffectHandler,
) : SideEffectHandler<CreatePinEvent, CreatePinSideEffect> {

    override fun getEventSource(): Flow<CreatePinEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: CreatePinSideEffect) {
        when (sideEffect) {
            is CreatePinSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is CreatePinSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
        }
    }
}
