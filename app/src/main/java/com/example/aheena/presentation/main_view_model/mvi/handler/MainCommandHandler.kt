package com.example.aheena.presentation.main_view_model.mvi.handler

import com.example.aheena.presentation.main_view_model.mvi.model.MainCommand
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale
import com.example.mvi.CommandHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainCommandHandler @Inject constructor() : CommandHandler<MainEvent, MainCommand> {
    private val commandSharedFlow = MutableSharedFlow<MainCommand>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return postListCommandHandler()
    }

    override suspend fun onCommand(command: MainCommand) {
        commandSharedFlow.emit(command)
    }

    private fun postListCommandHandler(): Flow<MainEvent> {
        return commandSharedFlow.flatMapMerge { command ->
            when (command) {
                is MainCommand.LoadAppTheme -> handleLoadAppTheme()
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
