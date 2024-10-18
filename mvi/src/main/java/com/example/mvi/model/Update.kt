package com.example.mvi.model

data class Update<State, SideEffect, UiCommand> internal constructor(
    val state: State?,
    val sideEffects: List<SideEffect>?,
    val uiCommands: List<UiCommand>?,
) {
    companion object {
        fun <State, SideEffect, UiCommand> nothing(): Update<State, SideEffect, UiCommand> =
            Update(null, null, null)

        fun <State, SideEffect, UiCommand> state(state: State) =
            Update<State, SideEffect, UiCommand>(
                state = state,
                sideEffects = null,
                uiCommands = null,
            )

        fun <State, SideEffect, UiCommand> sideEffects(sideEffects: List<SideEffect>) =
            Update<State, SideEffect, UiCommand>(
                state = null,
                sideEffects = sideEffects,
                uiCommands = null,
            )

        fun <State, SideEffect, UiCommand> uiCommands(uiCommands: List<UiCommand>) =
            Update<State, SideEffect, UiCommand>(
                state = null,
                sideEffects = null,
                uiCommands = uiCommands,
            )

        fun <State, SideEffect, UiCommand> stateWithSideEffectsWithCommands(
            state: State,
            sideEffects: List<SideEffect>,
            uiCommands: List<UiCommand>,
        ) =
            Update<State, SideEffect, UiCommand>(
                state = state,
                sideEffects = sideEffects,
                uiCommands = uiCommands,
            )
    }
}

