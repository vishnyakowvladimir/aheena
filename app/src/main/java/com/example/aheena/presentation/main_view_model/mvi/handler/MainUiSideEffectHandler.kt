package com.example.aheena.presentation.main_view_model.mvi.handler

import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.core.controller.theme.ThemeManager
import com.example.core.di.qualifier.MainRouter
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.core.navigation.router.NavRouter
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
    private val themeManager: ThemeManager,
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
                is MainSideEffect.Ui.ApplyTheme -> handleApplyTheme()
                is MainSideEffect.Ui.OpenAuthentication -> handleOpenAuthentication()
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<MainEvent.Ui> {
        return flow {
            mainRouter.popBackStack()
            emit(MainEvent.Ui.None)
        }
    }

    private fun handleApplyTheme(): Flow<MainEvent.Ui> {
        return flow {
            themeManager.applyThemeMode()
            emit(MainEvent.Ui.OnThemeApplied)
        }
    }

    private fun handleOpenAuthentication(): Flow<MainEvent.Ui> {
        return flow {
            mainRouter.replace(FeaturesDestination.AuthenticationDestination)
            emit(MainEvent.Ui.None)
        }
    }
}

