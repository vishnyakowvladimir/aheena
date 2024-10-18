package com.example.aheena.presentation.main_view_model.mvi.handler

import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainDomainSideEffectHandler @Inject constructor() :
    SideEffectHandler<MainEvent, MainSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<MainSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return postListCommandHandler()
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListCommandHandler(): Flow<MainEvent> {
        return sideEffectSharedFlow.flatMapMerge { command ->
            when (command) {
                is MainSideEffect.Domain.LoadAppTheme -> handleLoadAppTheme()
            }
        }
    }

    private fun handleLoadAppTheme(): Flow<MainEvent.Domain> {
        return flow {
            emit(ThemeData())
        }
            .map(MainEvent.Domain::OnAppThemeLoaded)
    }
}

internal data class ThemeData(
    val themeMode: AppThemeMode = AppThemeMode.LIGHT,
    val viewScale: ViewScale = ViewScale.M,
)
