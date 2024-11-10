package com.example.feature_main.presentation.main.mvi.handler

import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.model.MainSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainDomainSideEffectHandler @Inject constructor() :
    SideEffectHandler<MainEvent, MainSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<MainSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<MainEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            emptyFlow<MainEvent>()
        }
            .flowOn(Dispatchers.IO)
    }
}

