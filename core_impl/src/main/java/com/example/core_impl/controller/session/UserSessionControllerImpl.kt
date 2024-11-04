package com.example.core_impl.controller.session

import com.example.core.controller.session.UserSessionController
import com.example.core.di.qualifier.MainRouter
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.coroutines.AppCoroutineScope
import com.example.core.utils.extension.withLatestFrom
import com.example.core.utils.time.AppSystemClock
import com.example.data_sdk_api.interactor.user_activity.UserActivityInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

// если пользователь неактивен(не нажимал экран) в течение этого времени,
// тогда закрываем сессию(открываем экран ввода пин-кода)
private const val CRITICA_USER_INACTIVITY_DURATION = 30000L

@OptIn(FlowPreview::class)
class UserSessionControllerImpl @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    private val systemClock: AppSystemClock,
    private val userActivityInteractor: UserActivityInteractor,
    private val appCoroutineScope: AppCoroutineScope,
) : UserSessionController {

    private val isEnabledFlow = MutableStateFlow(false)

    init {
        subscribeUserActivity()
    }

    override fun enable() {
        isEnabledFlow.tryEmit(true)
        userActivityInteractor.setLastUserActivityTime(systemClock.getCurrentTimeMillis())
    }

    override fun disable() {
        isEnabledFlow.tryEmit(false)
    }

    private fun subscribeUserActivity() {
        userActivityInteractor
            .getLastUserActivityTime()
            .debounce(CRITICA_USER_INACTIVITY_DURATION)
            .withLatestFrom(isEnabledFlow) { millis, isEnabled ->
                val isCriticalUserInactivity =
                    systemClock.getCurrentTimeMillis() - millis >= CRITICA_USER_INACTIVITY_DURATION

                if (isEnabled && isCriticalUserInactivity) {
                    disable()
                    withContext(Dispatchers.Main) {
                        mainRouter.replaceAll(FeaturesDestination.AuthenticationDestination)
                    }
                }
            }
            .launchIn(appCoroutineScope)
    }
}
