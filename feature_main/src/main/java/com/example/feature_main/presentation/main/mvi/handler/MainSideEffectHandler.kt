package com.example.feature_main.presentation.main.mvi.handler

import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.model.MainSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class MainSideEffectHandler @Inject constructor(
    private val uiHandler: MainUiSideEffectHandler,
    private val domainHandler: MainDomainSideEffectHandler,
) : SideEffectHandler<MainEvent, MainSideEffect> {

    override fun getEventSource(): Flow<MainEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is MainSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
        }
    }
}
