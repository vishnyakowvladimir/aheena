package com.example.mvi

import com.example.mvi.model.Update

interface Reducer<Event, State, SideEffect, UiCommand> {
    fun update(state: State, event: Event): Update<State, SideEffect, UiCommand>
}
