package com.example.core_impl.deeplink

import android.net.Uri
import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.deeplink.Deeplink
import com.example.core_api.deeplink.DeeplinkHandler
import com.example.core_api.deeplink.DeeplinkLaunchMode
import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.pending_navigation.PendingNavigationManager
import com.example.core_api.pending_navigation.model.PendingNavigationState
import javax.inject.Inject

class DeeplinkHandlerImpl @Inject constructor(
    private val userSessionController: UserSessionController,
    private val deeplinks: Set<@JvmSuppressWildcards Deeplink>,
    @MainRouter private val router: NavRouter,
    private val pendingNavigationManager: PendingNavigationManager,
) : DeeplinkHandler {

    override fun handle(uri: Uri) {
        val deeplink = getDeeplink(uri)

        when {
            /**
             * Если что-то невалидно, тогда проверяем активна ли сессия.
             * Если сессия неактивна, то переходим в фичу авторизации.
             * Если сессия активна, то ничего делать не нужно.
             * */
            deeplink == null || !deeplink.isEnabled() || !deeplink.isValid(uri) -> {
                if (!userSessionController.isEnabled()) {
                    router.replaceAll(FeaturesDestination.AuthenticationDestination)
                }
            }

            /**
             * Если дефолтный режим, то авторизирован или нет - не имеет значения.
             * Просто переходим на экран.
             * */
            deeplink.launchMode == DeeplinkLaunchMode.DEFAULT -> deeplink.follow(uri)

            /**
             * Если сессия активна, то просто переходим на экран.
             * */
            userSessionController.isEnabled() -> Unit

            /**
             * Сюда попадаем, если сессия неактивна и нужна авторизация.
             * Переходим в фичу авторизации.
             *
             * */
            else -> {
                pendingNavigationManager.saveState(
                    PendingNavigationState.Action { deeplink.follow(uri) },
                )
                router.replaceAll(FeaturesDestination.AuthenticationDestination)
            }
        }
    }

    private fun getDeeplink(uri: Uri): Deeplink? {
        return deeplinks.firstOrNull { deeplink ->
            deeplink.host.lowercase() == getHost(uri)
        }
    }

    private fun getHost(uri: Uri): String {
        return "${uri.host}${uri.path}".lowercase()
    }
}
