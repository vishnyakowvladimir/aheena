package com.example.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class MviStore<Event, State, SideEffect, UiCommand>(
    private val stateMachine: StateMachine<Event, State, SideEffect, UiCommand>,
    private val sideEffectHandler: SideEffectHandler<Event, SideEffect>,
    private val transitionListener: TransitionListener<Event, State, SideEffect, UiCommand>? = null
) {
    private val eventSharedFlow = MutableSharedFlow<Event>(replay = Int.MAX_VALUE)

    suspend fun onEvent(event: Event) {
        eventSharedFlow.emit(event)
    }

    fun start(
        coroutineScope: CoroutineScope,
        actionState: suspend (State) -> Unit,
        actionUiCommand: suspend (UiCommand) -> Unit,
    ) {
        getStateSource()
            .onEach(actionState)
            .launchIn(coroutineScope)

        stateMachine.getUiCommandSource()
            .onEach(actionUiCommand)
            .launchIn(coroutineScope)

        stateMachine.getSideEffectSource()
            .onEach(sideEffectHandler::onSideEffect)
            .launchIn(coroutineScope)

        if (transitionListener != null) {
            stateMachine.getTransitionSource()
                .onEach { transitionListener.onTransition(it) }
                .launchIn(coroutineScope)
        }
    }

    private fun getStateSource(): Flow<State> {
        val eventsSource = createEventsSource()
        return stateMachine.getStateSource(eventsSource)
    }

    private fun createEventsSource(): Flow<Event> {
        return merge(eventSharedFlow, sideEffectHandler.getEventSource())
    }
}
