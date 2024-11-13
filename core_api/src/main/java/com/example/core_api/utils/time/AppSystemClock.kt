package com.example.core_api.utils.time

interface AppSystemClock {
    fun getCurrentTimeMillis(): Long
    fun getSinceBootElapsedRealtimeMillis(): Long
    fun getSinceBootUptimeMillis(): Long
}
