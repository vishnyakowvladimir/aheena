package com.example.feature_tech.presentation.select_url.mvi.handler

import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class SelectUrlSideEffectHandler @Inject constructor(
    private val uiHandler: SelectUrlUiSideEffectHandler,
    private val domainHandler: SelectUrlDomainSideEffectHandler,
) : SideEffectHandler<SelectUrlEvent, SelectUrlSideEffect> {

    override fun getEventSource(): Flow<SelectUrlEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: SelectUrlSideEffect) {
        when (sideEffect) {
            is SelectUrlSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is SelectUrlSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
        }
    }
}
