package com.example.feature_itunes.presentation.itunes.mvi.handler

import com.example.core.navigation.router.NavRouter
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesSideEffect
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
internal class ItunesUiSideEffectHandler @Inject constructor(
    private val navRouter: NavRouter,
) : SideEffectHandler<ItunesEvent, ItunesSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<ItunesSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<ItunesEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: ItunesSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<ItunesEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is ItunesSideEffect.Ui.Back -> handleBack()
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<ItunesEvent> {
        return flow {
            navRouter.popBackStack()
            emit(ItunesEvent.None)
        }
    }
}

