package com.example.mvi.model

data class Transition<Event, State, SideEffect, Command> internal constructor(
    val state: State,
    val event: Event,
    val updatedState: State,
    val sideEffects: List<SideEffect>,
    val commands: List<Command>
)

