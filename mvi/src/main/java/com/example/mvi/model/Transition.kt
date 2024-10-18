package com.example.mvi.model

data class Transition<Event, State, SideEffect, UiCommand> internal constructor(
    val state: State,
    val event: Event,
    val updatedState: State,
    val sideEffects: List<SideEffect>,
    val uiCommands: List<UiCommand>,
)

