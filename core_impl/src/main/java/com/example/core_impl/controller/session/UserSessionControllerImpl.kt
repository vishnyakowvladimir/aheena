package com.example.core_impl.controller.session

import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.utils.coroutines.AppCoroutineScope
import com.example.core_api.utils.extension.withLatestFrom
import com.example.core_api.utils.time.AppSystemClock
import com.example.data_sdk_api.interactor.user_activity.UserActivityInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

// если пользователь неактивен(не нажимал экран) в течение этого времени,
// тогда закрываем сессию(открываем экран ввода пин-кода)
private const val CRITICAL_USER_INACTIVITY_DURATION = 300000L

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
            .debounce(CRITICAL_USER_INACTIVITY_DURATION)
            .flowOn(Dispatchers.Default)
            .withLatestFrom(isEnabledFlow) { _, isEnabled ->
                if (isEnabled) {
                    disable()
                    withContext(Dispatchers.Main) {
                        mainRouter.replaceAll(FeaturesDestination.AuthenticationDestination)
                    }
                }
            }
            .launchIn(appCoroutineScope)
    }
}
