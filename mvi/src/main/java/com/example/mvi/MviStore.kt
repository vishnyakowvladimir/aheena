package com.example.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class MviStore<Event, State, SideEffect, Command>(
    private val stateMachine: StateMachine<Event, State, SideEffect, Command>,
    private val commandHandler: CommandHandler<Event, Command>,
    private val transitionListener: TransitionListener<Event, State, SideEffect, Command>? = null
) {
    private val eventSharedFlow = MutableSharedFlow<Event>()

    suspend fun onEvent(event: Event) {
        eventSharedFlow.emit(event)
    }

    fun start(
        coroutineScope: CoroutineScope,
        actionState: suspend (State) -> Unit,
        actionSideEffect: suspend (SideEffect) -> Unit,
    ) {
        getStateSource()
            .onEach(actionState)
            .launchIn(coroutineScope)

        stateMachine.getSideEffectSource()
            .onEach(actionSideEffect)
            .launchIn(coroutineScope)

        stateMachine.getCommandSource()
            .onEach(commandHandler::onCommand)
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
        return merge(eventSharedFlow, commandHandler.getEventSource())
    }
}
