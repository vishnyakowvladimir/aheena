package com.example.data_source_impl.storage.user_activity

import com.example.data_source_api.storage.user_activity.UserActivityStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class UserActivityStorageImpl @Inject constructor() : UserActivityStorage {

    private val lastUserActivityTimeInMillis = MutableSharedFlow<Long>(replay = 1)

    override fun setLastUserActivityTime(millis: Long) {
        lastUserActivityTimeInMillis.tryEmit(millis)
    }

    override fun getLastUserActivityTime(): Flow<Long> {
        return lastUserActivityTimeInMillis
    }
}
