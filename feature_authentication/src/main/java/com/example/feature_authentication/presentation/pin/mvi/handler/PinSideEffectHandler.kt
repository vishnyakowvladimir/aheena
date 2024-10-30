package com.example.feature_authentication.presentation.pin.mvi.handler

import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class PinSideEffectHandler @Inject constructor(
    private val uiHandler: PinUiSideEffectHandler,
    private val domainHandler: PinDomainSideEffectHandler,
) : SideEffectHandler<PinEvent, PinSideEffect> {

    override fun getEventSource(): Flow<PinEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: PinSideEffect) {
        when (sideEffect) {
            is PinSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is PinSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
        }
    }
}
