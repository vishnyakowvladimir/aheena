package com.example.aheena.deeplink

import android.net.Uri
import com.example.core_api.deeplink.AheenaDeeplink
import com.example.core_api.deeplink.DeeplinkLaunchMode
import javax.inject.Inject

class ItunesDeeplink @Inject constructor() : AheenaDeeplink {

    override val host: String = "itunes"
    override val launchMode: DeeplinkLaunchMode = DeeplinkLaunchMode.WITH_AUTHENTICATION

    override fun follow(uri: Uri): Boolean {
        return true
    }

    override fun isEnabled(uri: Uri): Boolean = true

    override fun isValid(uri: Uri): Boolean = true
}
