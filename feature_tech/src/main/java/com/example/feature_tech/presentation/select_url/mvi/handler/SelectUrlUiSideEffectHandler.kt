package com.example.feature_tech.presentation.select_url.mvi.handler

import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.router.NavRouter
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlSideEffect
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
internal class SelectUrlUiSideEffectHandler @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
) : SideEffectHandler<SelectUrlEvent, SelectUrlSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<SelectUrlSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<SelectUrlEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: SelectUrlSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<SelectUrlEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is SelectUrlSideEffect.Ui.Back -> handleBack()
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<SelectUrlEvent> {
        return flow {
            mainRouter.popBackStack()
            emit(SelectUrlEvent.None)
        }
    }
}

