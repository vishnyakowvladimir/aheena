package com.example.core_impl.controller.session

import com.example.core.controller.session.UserSessionController
import com.example.core.di.qualifier.MainRouter
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.coroutines.AppCoroutineScope
import com.example.core.utils.time.AppSystemClock
import com.example.data_sdk_api.interactor.user_activity.UserActivityInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val DELAY = 2000L
private const val WITHOUT_USER_ACTIVITY_DURATION = 10000

@OptIn(FlowPreview::class)
class UserSessionControllerImpl @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    private val systemClock: AppSystemClock,
    private val userActivityInteractor: UserActivityInteractor,
    appCoroutineScope: AppCoroutineScope,
) : UserSessionController {

    private var isEnable = false
    private var job: Job? = null

    init {
        userActivityInteractor.getLastUserActivityTime()
            .debounce(DELAY)
            .onEach { millis ->
                if (isEnable) {
                    start(millis)
                }
            }
            .launchIn(appCoroutineScope)
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
        delay(DELAY)
        if (currentMillis - millis < WITHOUT_USER_ACTIVITY_DURATION) {
            check(millis)
        }
    }
}
