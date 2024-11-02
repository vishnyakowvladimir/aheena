package com.example.data_sdk_api.interactor.user_activity

import kotlinx.coroutines.flow.SharedFlow

interface UserActivityInteractor {
    fun setLastUserActivityTime(millis: Long)
    fun getLastUserActivityTime(): SharedFlow<Long>
}
