package com.example.data_sdk_api.interactor.user_activity

import kotlinx.coroutines.flow.Flow

interface UserActivityInteractor {
    fun setLastUserActivityTime(millis: Long)
    fun getLastUserActivityTime(): Flow<Long>
}
