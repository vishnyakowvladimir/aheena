package com.example.feature_itunes.presentation.itunes.mvi.handler

import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesSideEffect
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
internal class ItunesDomainSideEffectHandler @Inject constructor() :
    SideEffectHandler<ItunesEvent, ItunesSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<ItunesSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<ItunesEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: ItunesSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<ItunesEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            emptyFlow<ItunesEvent>()
        }
            .flowOn(Dispatchers.IO)
    }
}

