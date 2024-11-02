package com.example.core_impl.utils.time

import android.os.SystemClock
import com.example.core.utils.time.AppSystemClock
import javax.inject.Inject

class AppSystemClockImpl @Inject constructor() : AppSystemClock {

    override fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    override fun getSinceBootElapsedRealtimeMillis(): Long {
        return SystemClock.elapsedRealtime()
    }

    override fun getSinceBootUptimeMillis(): Long {
        return SystemClock.uptimeMillis()
    }
}
