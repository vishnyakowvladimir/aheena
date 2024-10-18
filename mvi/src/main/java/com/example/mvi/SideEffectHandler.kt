package com.example.mvi

import kotlinx.coroutines.flow.Flow

interface SideEffectHandler<Event, SideEffect> {
    fun getEventSource(): Flow<Event>
    suspend fun onSideEffect(sideEffect: SideEffect)
}
