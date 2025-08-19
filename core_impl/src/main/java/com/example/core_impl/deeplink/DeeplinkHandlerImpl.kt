package com.example.core_impl.deeplink

import android.net.Uri
import android.util.Log
import com.example.core_api.deeplink.Deeplink
import com.example.core_api.deeplink.DeeplinkHandler
import javax.inject.Inject

class DeeplinkHandlerImpl @Inject constructor(
    private val deeplinks: Set<@JvmSuppressWildcards Deeplink>,
) : DeeplinkHandler {

    override fun handle(uri: Uri) {
        deeplinks.forEach { deeplink ->
            Log.d("check111", "${deeplink.host}")
        }
    }
}
