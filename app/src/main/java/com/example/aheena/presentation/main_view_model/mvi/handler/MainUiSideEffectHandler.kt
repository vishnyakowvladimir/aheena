package com.example.aheena.presentation.main_view_model.mvi.handler

import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.core.navigation.feature_destination.AuthenticationDestination
import com.example.core.navigation.router.AppRouter
import com.example.core.presentation.theme_manager.ThemeManager
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
    private val router: AppRouter,
    private val themeManager: ThemeManager,
) : SideEffectHandler<MainEvent, MainSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<MainSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<MainEvent> {
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
            router.popBackStack()
            emit(MainEvent.Ui.None)
        }
    }

    private fun handleApplyTheme(): Flow<MainEvent.Ui> {
        return flow {
            themeManager.applyThemeMode()
            emit(MainEvent.Ui.None)
        }
    }

    private fun handleOpenAuthentication(): Flow<MainEvent.Ui> {
        return flow {
            router.replace(AuthenticationDestination())
            emit(MainEvent.Ui.None)
        }
    }
}

