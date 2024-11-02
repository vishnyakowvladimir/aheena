package com.example.data_sdk_impl.interactor.user_activity

import com.example.data_sdk_api.interactor.user_activity.UserActivityInteractor
import com.example.data_source_api.repository.user_activity.UserActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserActivityInteractorImpl @Inject constructor(
    private val userActivityRepository: UserActivityRepository,
) : UserActivityInteractor {

    override fun setLastUserActivityTime(millis: Long) {
        userActivityRepository.setLastUserActivityTime(millis)
    }

    override fun getLastUserActivityTime(): Flow<Long> {
        return userActivityRepository.getLastUserActivityTime()
    }
}
