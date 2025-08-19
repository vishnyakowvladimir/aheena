package com.example.aheena.presentation.main_view_model.mvi.handler

import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.core_api.controller.theme.ThemeManager
import com.example.core_api.deeplink.DeeplinkHandler
import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.core_api.navigation.router.NavRouter
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
    private val deeplinkHandler: DeeplinkHandler,
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
                is MainSideEffect.Ui.HandleDeeplink -> handleDeeplink(sideEffect)
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

    private fun handleApplyTheme(): Flow<MainEvent> {
        return flow {
            themeManager.applyThemeMode()
            emit(MainEvent.Ui.OnThemeApplied)
        }
    }

    private fun handleOpenAuthentication(): Flow<MainEvent> {
        return flow {
            mainRouter.replace(FeaturesDestination.AuthenticationDestination)
            emit(MainEvent.None)
        }
    }

    private fun handleDeeplink(effect: MainSideEffect.Ui.HandleDeeplink): Flow<MainEvent> {
        return flow {
            deeplinkHandler.handle(effect.uri)
            emit(MainEvent.None)
        }
    }
}

