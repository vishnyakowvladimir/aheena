package com.example.mvi.model

data class Update<State, SideEffect> internal constructor(
    val state: State?,
    val sideEffects: List<SideEffect>?,
) {
    companion object {
        fun <State, SideEffect> nothing(): Update<State, SideEffect> =
            Update(null, null)

        fun <State, SideEffect> state(state: State) =
            Update<State, SideEffect>(
                state = state,
                sideEffects = null,
            )

        fun <State, SideEffect> sideEffects(vararg sideEffects: SideEffect) =
            Update<State, SideEffect>(
                state = null,
                sideEffects = sideEffects.ifEmpty { null }?.toList(),
            )

        fun <State, SideEffect> sideEffects(sideEffects: List<SideEffect>) =
            Update<State, SideEffect>(
                state = null,
                sideEffects = sideEffects,
            )

        fun <State, SideEffect> stateWithSideEffects(
            state: State? = null,
            sideEffects: List<SideEffect>? = null,
        ) = Update(state = state, sideEffects = sideEffects)
    }
}

