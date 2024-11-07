package com.example.feature_itunes.presentation.itunes.mvi.handler

import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class ItunesSideEffectHandler @Inject constructor(
    private val uiHandler: ItunesUiSideEffectHandler,
    private val domainHandler: ItunesDomainSideEffectHandler,
) : SideEffectHandler<ItunesEvent, ItunesSideEffect> {

    override fun getEventSource(): Flow<ItunesEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: ItunesSideEffect) {
        when (sideEffect) {
            is ItunesSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is ItunesSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
        }
    }
}
