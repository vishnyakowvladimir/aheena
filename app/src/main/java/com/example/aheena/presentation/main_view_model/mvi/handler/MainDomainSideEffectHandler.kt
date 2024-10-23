package com.example.aheena.presentation.main_view_model.mvi.handler

import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainDomainSideEffectHandler @Inject constructor() :
    SideEffectHandler<MainEvent, MainSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<MainSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<MainEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is MainSideEffect.Domain.LoadData -> handleLoadData()
            }
        }
    }

    private fun handleLoadData(): Flow<MainEvent.Domain> {
        return flow {
            delay(1000)
            emit(MainEvent.Domain.OnDataLoaded)
        }
    }
}

