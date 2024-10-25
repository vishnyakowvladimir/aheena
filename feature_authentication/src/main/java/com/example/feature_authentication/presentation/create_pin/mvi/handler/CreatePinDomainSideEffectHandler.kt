package com.example.feature_authentication.presentation.create_pin.mvi.handler

import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
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
internal class CreatePinDomainSideEffectHandler @Inject constructor() :
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
            flow<CreatePinEvent> { }
        }
            .flowOn(Dispatchers.IO)
    }

}

