package com.example.data_source_api.repository.user_activity

import kotlinx.coroutines.flow.SharedFlow

interface UserActivityRepository {
    fun setLastUserActivityTime(millis: Long)
    fun getLastUserActivityTime(): SharedFlow<Long>
}
