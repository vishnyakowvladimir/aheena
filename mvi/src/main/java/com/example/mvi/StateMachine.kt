package com.example.mvi

import com.example.mvi.model.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.scan

@OptIn(ExperimentalCoroutinesApi::class)
class StateMachine<Event, State, SideEffect, Command>(
    private val stateUpdater: Reducer<Event, State, SideEffect, Command>,
    private val initialState: State,
) {
    private val commandsSharedFlow = MutableSharedFlow<List<Command>>()
    private val sideEffectsSharedFlow = MutableSharedFlow<List<SideEffect>>()
    private val transitionSharedFlow =
        MutableSharedFlow<Transition<Event, State, SideEffect, Command>>(extraBufferCapacity = Int.MAX_VALUE)

    fun getStateSource(eventSource: Flow<Event>): Flow<State> {
        return eventSource.scan(initialState) { state, message ->
            val (newState, effects, commands) = stateUpdater.update(state, message)
            commands?.let { commandsSharedFlow.emit(it) }
            effects?.let { sideEffectsSharedFlow.emit(it) }
            sendTransition(state, message, newState, effects, commands)
            newState ?: state
        }
            .distinctUntilChanged { oldState, newState -> oldState === newState }
            .flowOn(Dispatchers.Default)
    }

    fun getSideEffectSource(): Flow<SideEffect> {
        return sideEffectsSharedFlow
            .flatMapMerge { it.asFlow().cancellable() }
    }

    fun getCommandSource(): Flow<Command> {
        return commandsSharedFlow.flatMapMerge { it.asFlow().cancellable() }
    }

    fun getTransitionSource(): Flow<Transition<Event, State, SideEffect, Command>> {
        return transitionSharedFlow
    }

    private suspend fun sendTransition(
        state: State,
        message: Event,
        newState: State?,
        sideEffects: List<SideEffect>?,
        commands: List<Command>?
    ) {
        val transition = Transition(
            state = state,
            event = message,
            updatedState = newState ?: state,
            sideEffects = sideEffects.orEmpty(),
            commands = commands.orEmpty()
        )
        transitionSharedFlow.emit(transition)
    }
}
