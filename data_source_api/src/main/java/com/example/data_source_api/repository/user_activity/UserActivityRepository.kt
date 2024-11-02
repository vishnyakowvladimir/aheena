package com.example.data_source_api.repository.user_activity

import kotlinx.coroutines.flow.Flow

interface UserActivityRepository {
    fun setLastUserActivityTime(millis: Long)
    fun getLastUserActivityTime(): Flow<Long>
}
