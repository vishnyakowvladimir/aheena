package com.example.data_source_impl.repository.user_activity

import com.example.data_source_api.repository.user_activity.UserActivityRepository
import com.example.data_source_api.storage.user_activity.UserActivityStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserActivityRepositoryImpl @Inject constructor(
    private val userActivityStorage: UserActivityStorage,
) : UserActivityRepository {

    override fun setLastUserActivityTime(millis: Long) {
        userActivityStorage.setLastUserActivityTime(millis)
    }

    override fun getLastUserActivityTime(): Flow<Long> {
        return userActivityStorage.getLastUserActivityTime()
    }
}
