package com.example.core_impl.deeplink

import android.net.Uri
import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.deeplink.Deeplink
import com.example.core_api.deeplink.DeeplinkHandler
import com.example.core_api.deeplink.DeeplinkLaunchMode
import javax.inject.Inject

class DeeplinkHandlerImpl @Inject constructor(
    private val userSessionController: UserSessionController,
    private val deeplinks: Set<@JvmSuppressWildcards Deeplink>,
) : DeeplinkHandler {

    override fun handle(uri: Uri) {
        val deeplink = getDeeplink(uri)
        when {
            deeplink == null || !deeplink.isEnabled() || !deeplink.isValid(uri) -> Unit
            deeplink.launchMode == DeeplinkLaunchMode.DEFAULT -> deeplink.follow(uri)
            !userSessionController.isEnabled() -> Unit
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
