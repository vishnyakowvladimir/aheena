package com.example.core_impl.controller.session

import android.util.Log
import com.example.core.controller.session.UserSessionController
import com.example.core.di.qualifier.MainRouter
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.time.AppSystemClock
import com.example.data_sdk_api.interactor.user_activity.UserActivityInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserSessionControllerImpl @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    private val systemClock: AppSystemClock,
    private val userActivityInteractor: UserActivityInteractor,
) : UserSessionController {

    private var isEnable = false
    private var job: Job? = null

    init {
        CoroutineScope(Dispatchers.Default + Job()).launch {
            userActivityInteractor.getLastUserActivityTime().collect { millis ->
                Log.d("check111", "onEach millis: $millis")
                if (isEnable) {
                    Log.d("check111", "isEnable onEach millis: $millis")
                    start(millis)
                }
            }
        }

//        userActivityInteractor.getLastUserActivityTime().onEach { millis ->
//            Log.d("check111", "onEach millis: $millis")
//            if (isEnable) {
//                Log.d("check111", "isEnable onEach millis: $millis")
//                start(millis)
//            }
//        }
    }

    override fun enable() {
        isEnable = true
        userActivityInteractor.setLastUserActivityTime(systemClock.getCurrentTimeMillis())
    }

    override fun disable() {
        isEnable = false
        job = null
    }

    private fun start(millis: Long) {
        job?.cancel()
        job = null

        val superVisorJob = CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {
            check(millis)
            withContext(Dispatchers.Main) {
                disable()
                mainRouter.replaceAll(FeaturesDestination.AuthenticationDestination)
            }
        }

        job = superVisorJob
    }

    private suspend fun check(millis: Long) {
        val currentMillis = systemClock.getCurrentTimeMillis()
        delay(2000)
        if (currentMillis < millis + 10000) {
            check(millis)
        }
    }
}
