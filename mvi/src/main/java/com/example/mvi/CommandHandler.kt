package com.example.mvi

import kotlinx.coroutines.flow.Flow

interface CommandHandler<Event, Command> {
    fun getEventSource(): Flow<Event>
    suspend fun onCommand(command: Command)
}
