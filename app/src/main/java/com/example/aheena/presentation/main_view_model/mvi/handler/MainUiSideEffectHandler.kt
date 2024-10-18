package com.example.aheena.presentation.main_view_model.mvi.handler

import androidx.navigation.NavHostController
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainUiSideEffectHandler @Inject constructor(
    private val navHostController: NavHostController,
) : SideEffectHandler<MainEvent, MainSideEffect.Ui> {
    private val commandSharedFlow = MutableSharedFlow<MainSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect.Ui) {
        commandSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<MainEvent> {
        return commandSharedFlow.flatMapMerge { command ->
            when (command) {
                is MainSideEffect.Ui.Back -> handleBack()
            }
        }
    }

    private fun handleBack(): Flow<MainEvent.Ui> {
        navHostController.popBackStack()
        return flow {
            emit(MainEvent.Ui.None)
        }
    }
}

