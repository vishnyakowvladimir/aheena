package com.example.mvi

import com.example.mvi.model.Transition

interface TransitionListener<Event, State, SideEffect> {
    fun onTransition(transition: Transition<Event, State, SideEffect>)
}
