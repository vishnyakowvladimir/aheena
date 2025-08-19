package com.example.core_impl.deeplink

import android.net.Uri
import com.example.core_api.deeplink.Deeplink
import com.example.core_api.deeplink.DeeplinkHandler
import javax.inject.Inject

class DeeplinkHandlerImpl @Inject constructor(
    private val deeplinks: Set<@JvmSuppressWildcards Deeplink>,
) : DeeplinkHandler {

    override fun handle(uri: Uri) {


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
