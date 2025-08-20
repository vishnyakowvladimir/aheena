package com.example.feature_main.presentation.main.mvi.handler

import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.pending_navigation.PendingNavigationManager
import com.example.core_api.pending_navigation.model.PendingNavigationState
import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.model.MainSideEffect
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
internal class MainUiSideEffectHandler @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    private val pendingNavigationManager: PendingNavigationManager,
) : SideEffectHandler<MainEvent, MainSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<MainSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<MainEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is MainSideEffect.Ui.Back -> handleBack()
                is MainSideEffect.Ui.PendingNavigationAction -> handlePendingNavigationAction(sideEffect)
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<MainEvent> {
        return flow {
            mainRouter.popBackStack()
            emit(MainEvent.None)
        }
    }

    private fun handlePendingNavigationAction(
        effect: MainSideEffect.Ui.PendingNavigationAction,
    ): Flow<MainEvent> {
        return flow {
            mainRouter.navigate(effect.state.destination)
            pendingNavigationManager.saveState(PendingNavigationState.None)
            emit(MainEvent.None)
        }
    }
}

