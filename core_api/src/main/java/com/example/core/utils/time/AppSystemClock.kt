package com.example.core.utils.time

interface AppSystemClock {
    fun getCurrentTimeMillis(): Long
    fun getSinceBootElapsedRealtimeMillis(): Long
    fun getSinceBootUptimeMillis(): Long
}
