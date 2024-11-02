package com.example.data_source_api.storage.user_activity

import kotlinx.coroutines.flow.SharedFlow

interface UserActivityStorage {
    fun setLastUserActivityTime(millis: Long)
    fun getLastUserActivityTime(): SharedFlow<Long>
}
